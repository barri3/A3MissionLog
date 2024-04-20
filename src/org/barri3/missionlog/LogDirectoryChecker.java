/** Copyright 2023 GameBrewer UK Limited, All rights reserved */

package org.barri3.missionlog;

import java.io.File;

/**
 *
 * @author GameBrewers UK Limited (gamebrewers.co.uk)
 * @version
 * @since 2023.06
 */
public class LogDirectoryChecker {
    private int fileTick = Integer.MAX_VALUE;
    private long current = -1;

    public boolean isTimeToCheck(int refreshAt) {
        return fileTick >= refreshAt;
    }
    
    public File scan(File root) {
        long needsToBe = current;
        File newFile = null;

        for (File file : root.listFiles()) {
            if (file.isFile()) {
                String fileName = file.getName();

                if (fileName.startsWith("Arma3_") && fileName.endsWith(".rpt")) {
                    String ageString = fileName.substring(10, fileName.length() - 4).replaceAll("-", "").replaceAll("_", "");
                    long age = Long.parseLong(ageString);

                    if (age > needsToBe) {
                        newFile = file;
                        needsToBe = age;
                    }
                }
            }
        }

        if (newFile != null) {
            current = needsToBe;
        }
        
        return newFile;
    }
    
    public void tick() {
        fileTick++;
    }
}
