package io.github.jochyoua.allseeingeye;

import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AllSeeingEyeUtils {

    private AllSeeingEyeUtils() {
        throw new UnsupportedOperationException("Cannot instantiate utility class.");
    }

    public static void logMessage(String message) {
        AllSeeingEyeUtils.logMessage(message, "debugLogs");
    }

    public static void logMessage(String message, String fileName, String playerName) {
        File directory = new File(AllSeeingEye.getPlugin(AllSeeingEye.class).getDataFolder(), "logs/" + playerName);
        if (!directory.exists() && !directory.mkdirs()) {
            return;
        }
        AllSeeingEyeUtils.logMessage(message, playerName + "/" + fileName);
    }

    public static void logMessage(String message, String fileName) {
        StackTraceElement st = Thread.currentThread().getStackTrace()[2];
        Logger logger = Logger.getLogger(st.getClassName() + ":" + st.getLineNumber() + " " + Thread.currentThread().getStackTrace()[2].getMethodName());
        try {
            File file = new File(AllSeeingEye.getPlugin(AllSeeingEye.class).getDataFolder(), "logs/" + fileName + ".log");
            File pluginDirectory = AllSeeingEye.getPlugin(AllSeeingEye.class).getDataFolder();
            File directory = new File(AllSeeingEye.getPlugin(AllSeeingEye.class).getDataFolder(), "logs/");
            if (!pluginDirectory.exists() && !pluginDirectory.mkdirs()) {
                return;
            }
            if (!directory.exists() && !directory.mkdirs()) {
                return;
            }
            if (!file.exists() && !file.createNewFile()) {
                return;
            }
            FileHandler fileHandler = new FileHandler(file.getPath(), true);
            fileHandler.setFormatter(new SimpleFormatter() {
                @Override
                public String format(LogRecord logRecord) {
                    ZonedDateTime zdt = ZonedDateTime.ofInstant(
                            Instant.ofEpochMilli(logRecord.getMillis()), ZoneId.systemDefault());
                    String source;
                    source = logRecord.getLoggerName();
                    String message = formatMessage(logRecord);
                    return String.format("%1$tb %1$td, %1$tY %1$tl:%1$tM:%1$tS %1$Tp %2$s%n%4$s: %5$s%n",
                            zdt,
                            source,
                            logRecord.getLoggerName(),
                            logRecord.getLevel().getLocalizedName(),
                            message);
                }
            });

            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
            logger.setLevel(Level.INFO);

            logger.log(Level.INFO, message + "\n");
            fileHandler.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String formatMessage(String messageContents) {
        return ChatColor.translateAlternateColorCodes('&', messageContents);
    }

    public static String formatMessage(String messageContents, String... replacements) {
        return ChatColor.translateAlternateColorCodes('&', MessageFormat.format(messageContents, (Object[]) replacements));
    }

    public static List<String> formatMessage(List<String> messageContentList, String... replacements) {
        messageContentList.replaceAll(pattern -> ChatColor.translateAlternateColorCodes('&', MessageFormat.format(pattern, (Object[]) replacements)));
        return messageContentList;
    }

    public static List<String> formatMessage(List<String> messageContentList) {
        messageContentList.replaceAll(textToTranslate -> ChatColor.translateAlternateColorCodes('&', textToTranslate));
        return messageContentList;
    }
}
