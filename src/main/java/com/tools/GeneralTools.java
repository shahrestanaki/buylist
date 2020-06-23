package com.tools;

import com.service.search.SearchCriteria;
import com.service.search.SearchCriteriaList;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class GeneralTools {
    private static final String ENCRYPT_TOKEN = "WkKuGjJxCpfndb4v";

    public static String createRandom(String type, int count) {
        String randStr = "";
        switch (type) {
            case "number":
                randStr = "0123456789";
                break;
            case "lowercase":
                randStr = "abcdefghijklmnopqrstuvwxyz";
                break;
            case "uppercase":
                randStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                break;
            case "all":
                randStr = "ABCDEFGHIJKL0123MNOPQRSTUVWXYZabcdefghijklm0123nopqrstuvwxyzx789";
                break;
            default:
                break;
        }
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * randStr.length());
            builder.append(randStr.charAt(character));
        }
        return builder.toString();
    }

    public static int createRandom(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    public static <R extends SearchCriteriaList> SearchCriteriaList convertToCriteriaList(R test, String... except) {
        List<String> notInclude = new ArrayList<>();
        notInclude.addAll(Arrays.asList(except));
        notInclude.add("serialVersionUID");
        //------------
        SearchCriteriaList search = new SearchCriteriaList(test.getPage(), test.getSize(), test.getSort());
        HashSet<SearchCriteria> filter = new HashSet<>();
        //------------
        for (Field f : test.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.get(test) instanceof String || f.get(test) instanceof Long || f.get(test) instanceof Integer || f.get(test) instanceof Boolean) {
                    String value = f.get(test).toString();
                    String name = f.getName();
                    //------------
                    if (!notInclude.contains(name) && !StringUtils.isEmpty(name) && !StringUtils.isEmpty(value)) {
                        if (f.get(test) instanceof String) {
                            if (name.startsWith("start") && value.contains("/") && value.length() == 10) {
                                //filter.add(new SearchCriteria(name.substring(5, 6).toLowerCase().concat(name.substring(6)), ">", GeneralUtilityService.shamsiTomailadiByTime(value, "00:00:01")));
                            } else if (name.startsWith("end") && value.contains("/") && value.length() == 10) {
                                //     filter.add(new SearchCriteria(name.substring(3, 4).toLowerCase().concat(name.substring(4)), "<", GeneralUtilityService.shamsiTomailadiByTime(value, "23:59:59")));
                            } else {
                                filter.add(new SearchCriteria(name, ":", value));
                            }
                        } else {
                            filter.add(new SearchCriteria(name, ":", value));
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        search.setSearch(filter);
        return search;
    }

    public static String encrypt(String value, String key) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(ENCRYPT_TOKEN.getBytes(StandardCharsets.UTF_8), 0, 16);
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), 0, 16, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(value.getBytes());
        System.out.println("encrypted = " + Base64.getEncoder().encodeToString(encrypted));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String encrypted, String key) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(ENCRYPT_TOKEN.getBytes(StandardCharsets.UTF_8), 0, 16);
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), 0, 16, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
        System.out.println("new String(original) = " + new String(original));
        return new String(original);
    }
}
