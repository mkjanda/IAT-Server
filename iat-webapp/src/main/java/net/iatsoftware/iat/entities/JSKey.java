package net.iatsoftware.iat.entities;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "JSKey")
@XmlAccessorType(XmlAccessType.NONE)
@Component
@Scope("prototype")
public class JSKey implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
/*
    @Inject
    JSAES aes;

    private byte[][] words = new byte[WORD_COUNT][];
    public static final int WORD_COUNT = 4;
    public static final int WORD_SIZE = 4;

    @JsonProperty("keyWords")
    public List<byte[]> getKeyWords() {
        var words = new ArrayList<Long>();
        for (var bi : keyWords)
            words.add(bi.longValue());
        return words;
    }


    @JsonIgnore
    @XmlElements(@XmlElement(name="KeyWords"))
    public String[] getKeyWordStrings() {
        String[] wordStrings = new String[WORD_COUNT];
        for (int ctr = 0; ctr < WORD_COUNT; ctr++) {
            wordString[ctr] = new BigInteger();
        }
        if (keyWordStrings.size() == WORD_COUNT)
            return keyWordStrings;
        keyWordStrings.clear();
        for (BigInteger bi : keyWords) {
            keyWordStrings.add(bi.toString(16));
        }
        String[] stringArray = new String[WORD_COUNT];
        return keyWordStrings.toArray(stringArray);
    }
    public void setKeyWordStrings(String[] words) {
        keyWords.clear();
        keyWordStrings.clear();
        keyWordStrings.addAll(Arrays.asList(words));
        for (var word : words) 
            keyWords.add(new BigInteger(word, 16));
    }

    public void generateKey() {
        this.words = new byte[WORD_COUNT][];
        byte[] keyBytes = new byte[WORD_SIZE * WORD_COUNT];
        random.nextBytes(keyBytes);
        for (int ctr = 0; ctr < WORD_COUNT; ctr++) {
            this.words[ctr] = new byte[WORD_SIZE];
            System.arraycopy(keyBytes, ctr * WORD_SIZE, words[ctr], 0, WORD_SIZE);
        }
    }

    public JSKey() {
        for (int ctr = 0; ctr < WORD_COUNT; ctr++)
            words[ctr] = new byte[WORD_SIZE];
    }

    public void setKeyWords(byte[][] words) {
        this.words = words;
    }

    public void encryptWords(String[] cipher) {
        try {
            if (keyWords.isEmpty()) {
                generateKey();
            }
            String[] encWordStrings = aes.encryptWordSet(getKeyWordStrings(), cipher);
            keyWords.clear();
            for (String word : encWordStrings) {
                keyWords.add(new BigInteger(word, 16));
            }
        } catch (java.security.InvalidParameterException ex) {
            logger.error("Failed to encrypt JSKey", ex);
        }
    }*/
}
