/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.messaging;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import net.iatsoftware.iat.generated.ManifestEntityType;
import net.iatsoftware.iat.generated.ResourceType;
import org.apache.logging.log4j.LogManager;
/**
 *
 * @author Michael Janda
 */

import org.apache.logging.log4j.Logger;

@XmlRootElement(name = "Manifest")
@XmlAccessorType(XmlAccessType.NONE)
public class Manifest extends net.iatsoftware.iat.generated.ManifestPojo {
	private static final Logger logger = LogManager.getLogger();
	private static final String NOTIFICATION_FILE = "notification.html";
	private static final String NOTIFICATION_FLAG_FILE = "notification-flags.txt";
	int walkNdx;

	public Manifest() {
		iatName = "none";
	}

	public Manifest(String testName) {
		iatName = testName;
	}

	public Manifest(String iatName, java.io.File directory) {
		iatName = "none";
		for (FileEntity fe : accumulateManifest(directory)) {
			if (fe.getEntityType().equals(ManifestEntityType.DIRECTORY))
				getDirectory().add((Directory) fe);
			else
				getFile().add((File) fe);
		}
	}

	/*
	 * private long calcSize(Directory directory) {
	 * long size = 0;
	 * for (var d : directory.getDirectory())
	 * size += calcSize(d);
	 * for (var f : directory.getFile())
	 * size += f.getSize();
	 * return size;
	 * }
	 */
	public List<FileEntity> accumulateManifest(java.io.File directory) {
		var l = new ArrayList<FileEntity>();
		for (java.io.File f : directory.listFiles()) {
			if (f.isDirectory()) {
				var dir = new Directory();
				dir.setName(f.getName());
				dir.setPath(f.getAbsolutePath());
				dir.setEntityType(ManifestEntityType.DIRECTORY);
				l.add(dir);
				accumulateManifestDirectory(dir);
			} else {
				var file = new File();
				file.setPath(f.getAbsolutePath());
				file.setName(f.getName());
				file.setEntityType(ManifestEntityType.UPDATE_FILE);
				file.setResourceType(ResourceType.UPDATE_FILE);
				file.setSize((int)f.getTotalSpace());
				l.add(file);
			}
		}
		return l;
	}

	public void accumulateManifestDirectory(Directory directory) {
		for (var fe : new java.io.File(directory.getPath()).listFiles()) {
			if (fe.isDirectory()) {
				var dir = new Directory();
				dir.setName(fe.getName());
				dir.setPath(fe.getAbsolutePath());
				dir.setEntityType(ManifestEntityType.DIRECTORY);
				directory.getDirectory().add(dir);
				accumulateManifestDirectory(dir);
			} else {
				var file = new File();
				file.setPath(fe.getAbsolutePath());
				file.setName(fe.getName());
				file.setEntityType(ManifestEntityType.UPDATE_FILE);
				file.setResourceType(ResourceType.UPDATE_FILE);
				file.setSize((int)fe.getTotalSpace());
				directory.getFile().add(file);
			}
		}
	}

	public Manifest(java.io.File[] files) {
	}

	private static java.io.File[] getDepreciatedUpdateDirectoryCandidates(
			java.io.File rootPath,
			String clientRelease) {
		java.io.File[] updateDirectories = rootPath.listFiles();
		final Pattern patt = Pattern.compile(
				"([1-9][0-9]*)\\.([0-9]+)\\.([0-9]+)\\.([0-9]+)$");
		final Pattern dirPattern = Pattern.compile(
				".+\\.([1-9][0-9]*)-([0-9]+)-([0-9]+)-([0-9]+)$");
		Matcher matcher = patt.matcher(clientRelease);
		matcher.find();
		final int clientVersion = Integer.parseInt(matcher.group(1));
		final int clientMajor = Integer.parseInt(matcher.group(2));
		final int clientMinor = Integer.parseInt(matcher.group(3));
		final int clientRevision = Integer.parseInt(matcher.group(4));
		java.io.File[] filteredDirectories = Arrays
				.stream(updateDirectories)
				.filter(
						ud -> {
							try {
								int dirVersion, dirMajor, dirMinor, dirRevision;
								Matcher dirMatcher = dirPattern.matcher(ud.getName());
								if (!dirMatcher.matches())
									throw new RuntimeException(
											"Invalid directory in update path: " + ud.getName());
								dirVersion = Integer.parseInt(dirMatcher.group(1));
								dirMajor = Integer.parseInt(dirMatcher.group(2));
								dirMinor = Integer.parseInt(dirMatcher.group(3));
								dirRevision = Integer.parseInt(dirMatcher.group(4));
								if (dirVersion != clientVersion)
									return false;
								if (dirMajor != clientMajor)
									return false;
								if (dirMinor > clientMinor)
									return true;
								if (dirRevision > clientRevision)
									return true;
								return false;
							} catch (RuntimeException ex) {
								logger.error(ex);
								return false;
							}
						})
				.toArray(java.io.File[]::new);
		Arrays.sort(
				filteredDirectories,
				(d1, d2) -> {
					int version1, major1, minor1, revision1, version2, major2, minor2, revision2;
					Matcher m1 = dirPattern.matcher(d1.getName());
					if (!m1.matches())
						throw new RuntimeException(
								"Invalid directory in update path: " + d1.getName());
					version1 = Integer.parseInt(m1.group(1));
					major1 = Integer.parseInt(m1.group(2));
					minor1 = Integer.parseInt(m1.group(3));
					revision1 = Integer.parseInt(m1.group(4));
					Matcher m2 = dirPattern.matcher(d2.getName());
					if (!m2.matches())
						throw new RuntimeException(
								"Invalid directory in update path: " + d2.getName());
					version2 = Integer.parseInt(m2.group(1));
					major2 = Integer.parseInt(m2.group(2));
					minor2 = Integer.parseInt(m2.group(3));
					revision2 = Integer.parseInt(m2.group(4));
					if (version2 != version1)
						return version2 - version1;
					if (major2 != major1)
						return major2 - major1;
					if (minor2 != minor1)
						return minor2 - minor1;
					if (revision2 != revision1)
						return revision2 - revision1;
					return 0;
				});
		return filteredDirectories;
	}

	private static java.io.File[] getUpdateDirectoryCandidates(
			java.io.File rootPath,
			String clientRelease) {
		java.io.File[] updateDirectories = rootPath.listFiles();
		final Pattern patt = Pattern.compile(
				"([1-9][0-9]*)\\.([0-9]+)\\.([0-9]+)\\.([0-9]+)$");
		final Pattern dirPattern = Pattern.compile(
				".+\\.([1-9][0-9]*)-([0-9]+)-([0-9]+)-([0-9]+)$");
		Matcher matcher = patt.matcher(clientRelease);
		matcher.find();
		final int clientVersion = Integer.parseInt(matcher.group(1));
		final int clientMajor = Integer.parseInt(matcher.group(2));
		final int clientMinor = Integer.parseInt(matcher.group(3));
		final int clientRevision = Integer.parseInt(matcher.group(4));
		java.io.File[] filteredDirectories = Arrays
				.stream(updateDirectories)
				.filter(
						ud -> {
							int dirVersion, dirMajor, dirMinor, dirRevision;
							Matcher dirMatcher = dirPattern.matcher(ud.getName());
							if (!dirMatcher.matches())
								throw new RuntimeException(
										"Invalid directory in update path: " + ud.getName());
							dirVersion = Integer.parseInt(dirMatcher.group(1));
							dirMajor = Integer.parseInt(dirMatcher.group(2));
							dirMinor = Integer.parseInt(dirMatcher.group(3));
							dirRevision = Integer.parseInt(dirMatcher.group(4));
							if (dirVersion > clientVersion)
								return true;
							else if (dirVersion < clientVersion)
								return false;
							if (dirMajor > clientMajor)
								return true;
							else if (dirMajor < clientMajor)
								return false;
							if (dirMinor > clientMinor)
								return true;
							else if (dirMinor < clientMinor)
								return false;
							return (dirRevision > clientRevision);
						})
				.toArray(java.io.File[]::new);
		Arrays.sort(
				filteredDirectories,
				(d1, d2) -> {
					int version1, major1, minor1, revision1, version2, major2, minor2, revision2;
					Matcher m1 = dirPattern.matcher(d1.getName());
					if (!m1.matches())
						throw new RuntimeException(
								"Invalid directory in update path: " + d1.getName());
					version1 = Integer.parseInt(m1.group(1));
					major1 = Integer.parseInt(m1.group(2));
					minor1 = Integer.parseInt(m1.group(3));
					revision1 = Integer.parseInt(m1.group(4));
					Matcher m2 = dirPattern.matcher(d2.getName());
					if (!m2.matches())
						throw new RuntimeException(
								"Invalid directory in update path: " + d2.getName());
					version2 = Integer.parseInt(m2.group(1));
					major2 = Integer.parseInt(m2.group(2));
					minor2 = Integer.parseInt(m2.group(3));
					revision2 = Integer.parseInt(m2.group(4));
					if (version2 != version1)
						return version2 - version1;
					if (major2 != major1)
						return major2 - major1;
					if (minor2 != minor1)
						return minor2 - minor1;
					if (revision2 != revision1)
						return revision2 - revision1;
					return 0;
				});
		return filteredDirectories;
	}

	public static byte[] getUpdate(java.io.File rootPath, String clientRelease)
			throws java.io.IOException {
		final List<String> includedFiles = new ArrayList<>();
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		byte[] buff = new byte[8192];
		int nBytes;
		long nBytesRead, len;
		java.io.File[] updateDirectoryCandidates = Manifest.getUpdateDirectoryCandidates(
				rootPath,
				clientRelease);
		for (java.io.File dir : updateDirectoryCandidates) {
			java.io.File[] files = Arrays
					.stream(dir.listFiles())
					.filter(
							f -> !includedFiles.contains(f.getName()) &&
									!f.getName().equals(NOTIFICATION_FILE) &&
									!f.getName().equals(NOTIFICATION_FLAG_FILE))
					.toArray(java.io.File[]::new);
			for (java.io.File file : files) {
				includedFiles.add(file.getName());
				len = file.length();
				nBytesRead = 0;
				try (FileInputStream fIn = new FileInputStream(file)) {
					while (nBytesRead < len) {
						nBytes = fIn.read(
								buff,
								0,
								(len - nBytesRead < 8192) ? (int) (len - nBytesRead) : 8192);
						nBytesRead += nBytes;
						bOut.write(buff, 0, nBytes);
					}
				}
			}
		}
		return bOut.toByteArray();
	}

	public static byte[] getDepreciatedUpdate(
			java.io.File rootPath,
			String clientRelease)
			throws java.io.IOException {
		final List<String> includedFiles = new ArrayList<>();
		byte[] buff = new byte[8192];
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		int nBytes;
		long nBytesRead, len;
		java.io.File[] updateDirectoryCandidates = Manifest.getDepreciatedUpdateDirectoryCandidates(
				rootPath,
				clientRelease);
		for (java.io.File dir : updateDirectoryCandidates) {
			java.io.File[] files = Arrays
					.stream(dir.listFiles())
					.filter(f -> !includedFiles.contains(f.getName()))
					.toArray(java.io.File[]::new);
			for (java.io.File file : files) {
				includedFiles.add(file.getName());
				len = file.length();
				nBytesRead = 0;
				try (FileInputStream fIn = new FileInputStream(file)) {
					while (nBytesRead < len) {
						nBytes = fIn.read(
								buff,
								0,
								(len - nBytesRead < 8192) ? (int) (len - nBytesRead) : 8192);
						nBytesRead += nBytes;
						bOut.write(buff, 0, nBytes);
					}
				}
			}
		}
		return bOut.toByteArray();
	}

	public static Manifest getDepreciatedUpdateManifest(
			java.io.File rootPath,
			String clientRelease) {
		final Manifest updateManifest = new Manifest();
		java.io.File[] updateDirectoryCandidates = Manifest.getDepreciatedUpdateDirectoryCandidates(
				rootPath, clientRelease);
		for (java.io.File dir : updateDirectoryCandidates) {
			java.io.File[] dirFiles = dir.listFiles();
			Arrays.spliterator(dirFiles).forEachRemaining(f -> {
				var file = new File();
				file.setName(f.getName());
				file.setPath(f.getAbsolutePath());
				file.setResourceType(ResourceType.UPDATE_FILE);
				file.setMimeType("update-file/octet-stream");
				file.setSize((int)f.getTotalSpace());
				if (!updateManifest.containsFileWithName(f.getName()))
					updateManifest.addFile(file);
			});
		}
		return updateManifest;
	}

	public static Manifest getUpdateManifest(
			java.io.File rootPath,
			String clientRelease) {
		final Manifest updateManifest = new Manifest();
		java.io.File[] updateDirectoryCandidates = Manifest.getUpdateDirectoryCandidates(
				rootPath,
				clientRelease);
		for (java.io.File dir : updateDirectoryCandidates) {
			java.io.File[] dirFiles = dir.listFiles();
			Arrays
					.spliterator(dirFiles)
					.forEachRemaining(
							f -> {
								var file = new File();
								file.setName(f.getName());
								file.setPath(f.getAbsolutePath());
								file.setResourceType(ResourceType.UPDATE_FILE);
								file.setMimeType("update-file/octet-stream");
								file.setSize((int)f.getTotalSpace());
								if (!updateManifest.containsFileWithName(f.getName()))
									updateManifest.addFile(file);
							});
		}
		return updateManifest;
	}

	public static UpdateNotification getUpdateNotification(
			java.io.File rootPath,
			String clientRelease) {
		UpdateNotification updateNotification = new UpdateNotification();
		java.io.File[] notificationDirectoryCandidates = Manifest.getUpdateDirectoryCandidates(
				rootPath,
				clientRelease);
		byte[] buff = new byte[8192];
		Pattern versionPattern = Pattern.compile(
				"[0-9]+\\-[0-9]+\\-[0-9]+\\-[0-9]+");
		Arrays
				.stream(notificationDirectoryCandidates)
				.sorted(
						(a, b) -> {
							Pattern segPattern = Pattern.compile("[0-9]+");
							Matcher aMatcher = versionPattern.matcher(a.getName());
							aMatcher.find();
							String aVersion = aMatcher.group();
							Matcher bMatcher = versionPattern.matcher(b.getName());
							bMatcher.find();
							String bVersion = bMatcher.group();
							aMatcher = segPattern.matcher(aVersion);
							bMatcher = segPattern.matcher(bVersion);
							aMatcher.find();
							bMatcher.find();
							int aVer = Integer.parseInt(aMatcher.group());
							int bVer = Integer.parseInt(bMatcher.group());
							if (aVer != bVer)
								return bVer - aVer;
							aMatcher.find();
							bMatcher.find();
							aVer = Integer.parseInt(aMatcher.group());
							bVer = Integer.parseInt(bMatcher.group());
							if (aVer != bVer)
								return bVer - aVer;
							aMatcher.find();
							bMatcher.find();
							aVer = Integer.parseInt(aMatcher.group());
							bVer = Integer.parseInt(bMatcher.group());
							if (aVer != bVer)
								return bVer - aVer;
							aMatcher.find();
							bMatcher.find();
							aVer = Integer.parseInt(aMatcher.group());
							bVer = Integer.parseInt(bMatcher.group());
							return bVer - aVer;
						})
				.forEach(
						dir -> {
							java.io.File[] dirFiles = dir.listFiles();
							Matcher versionMatcher = versionPattern.matcher(dir.getName());
							versionMatcher.find();
							UpdateNotification.Notification notification = new UpdateNotification.Notification();
							notification.setVersion(
									versionMatcher.group().replaceAll("\\-", "."));
							notification.setFlags(BigInteger.ZERO);
							Arrays
									.spliterator(dirFiles)
									.forEachRemaining(
											f -> {
												if (f.getName().equals(NOTIFICATION_FILE)) {
													try (FileInputStream fIn = new FileInputStream(f)) {
														String notificationText = "";
														int nBytesRead = 0;
														int bytesRead = 0;
														while (nBytesRead < f.length()) {
															bytesRead = fIn.read(
																	buff,
																	0,
																	(f.length() > nBytesRead + 8192)
																			? 8192
																			: (int) (f.length() - nBytesRead));
															notificationText += new String(buff, 0, bytesRead, "UTF-8");
															nBytesRead += bytesRead;
														}
														notification.setValue(notificationText);
														updateNotification.getNotification().add(notification);
													} catch (java.io.IOException ex) {
														logger.error(
																"Error retrieving update notification for "
																		+ f.getName(),
																ex);
													}
												}
												if (f.getName().equals(NOTIFICATION_FLAG_FILE)) {
													try (FileReader fileReader = new FileReader(f)) {
														try (
																BufferedReader bReader = new BufferedReader(
																		fileReader)) {
															notification.setFlags(new BigInteger(bReader.readLine()));
														} catch (java.io.IOException ex) {
															logger.error(
																	"Error retrieving update notification for " +
																			f.getName(),
																	ex);
														}
													} catch (java.io.IOException ex) {
														logger.error(
																"Error retrieving update notification for "
																		+ f.getName(),
																ex);
													}
												}
											});
						});
		return updateNotification;
	}

	public List<File> getFiles(Directory dir) {
		List<File> fList = new ArrayList<>();
		for (var d : dir.getDirectory())
			fList.addAll(getFiles(d));
		fList.addAll(this.getFiles());
		return fList;
	}

	public List<File> getFiles() {
		List<File> fList = new ArrayList<File>();
		for (Directory d : this.getDirectory()) {
			fList.addAll(getFiles(d));
		}
		for (File f : this.getFile()) {
			fList.add(f);
		}
		return fList;
	}

	private List<Directory> getSubDirectories(Directory d) {
		List<Directory> dList = new ArrayList<>();
		for (Directory dir : d.getDirectory()) {
			dList.add(dir);
			dList.addAll(getSubDirectories(dir));
		}
		return dList;
	}

	public List<Directory> getDirectories() {
		List<Directory> dList = new ArrayList<>();
		for (Directory dir : this.getDirectory()) {
			dList.add(dir);
			dList.addAll(getSubDirectories(dir));
		}
		return dList;
	}

	public boolean containsFileWithName(String filename) {
		return getFiles().stream().anyMatch(f -> f.getName().equals(filename));
	}

	public void addFile(File f) {
		this.getFile().add(f);
	}
}
