/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.controllers;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.entities.TestResource;
import net.iatsoftware.iat.repositories.IATRepositoryManager;
import net.iatsoftware.iat.services.DeploymentService;

import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.inject.Inject;

@Controller
@RequestMapping("/Upload")
public class DeploymentUploadController {

	@Inject
	DeploymentService deploymentService;

	@Inject
	IATRepositoryManager repositoryManager;

	@PostMapping("/Deployment")
	public ResponseEntity<Void> deploymentUpload(@RequestParam("deploymentId") Long deploymentId,
			@RequestBody byte[] data) throws java.io.IOException {
		var deploymentSession = repositoryManager.getDeploymentSession(deploymentId);
		var deployer = deploymentService.getDeployer(deploymentId);
		var sessionState = deployer.session();
		var test = deploymentSession.getTest();
		int offset = 0;
		for (var f : sessionState.fileManifest().getFiles()) {
			int fSize = f.getSize();
			byte[] fData = new byte[fSize];
			System.arraycopy(data, offset, fData, 0, fSize);
			offset += fSize;
			var testResource = new TestResource(test, f.getResourceId(), f.getMimeType(), fData, f.getResourceType());
			repositoryManager.addTestResource(testResource);
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping("/ItemSlides")
	public ResponseEntity<Void> itemSlidesUpload(@RequestParam("deploymentId") Long deploymentId,
			@RequestBody byte[] data) throws java.io.IOException {
		var deploymentSession = repositoryManager.getDeploymentSession(deploymentId);
		var deployer = deploymentService.getDeployer(deploymentId);
		var sessionState = deployer.session();
		var test = deploymentSession.getTest();
		int offset = 0;
		for (var f : sessionState.itemSlideManifest().getFiles()) {
			int fSize = f.getSize();
			byte[] fData = new byte[fSize];
			System.arraycopy(data, offset, fData, 0, fSize);
			offset += fSize;
			var testResource = new TestResource(test, f.getMimeType(), fData, f.getResourceType());
			repositoryManager.addTestResource(testResource);
		}
		return ResponseEntity.ok().build();
	}

	@ExceptionHandler(java.io.IOException.class)
	public ResponseEntity<Void> onException() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}
