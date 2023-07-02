import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        StringBuilder result = new StringBuilder();
        //1. В папке Games создайте несколько директорий: src, res, savegames, temp.
        File dirGames = new File("D://netology", "Games");
        result.append(createDirectory(dirGames));

        File dirSrc = new File(dirGames.getPath(), "src");
        result.append(createDirectory(dirSrc));

        File dirRes = new File(dirGames.getPath(), "res");
        result.append(createDirectory(dirRes));

        File dirSavegames = new File(dirGames.getPath(), "savegames");
        result.append(createDirectory(dirSavegames));

        File dirTemp = new File(dirGames.getPath(), "temp");
        result.append(createDirectory(dirTemp));

        //2. В каталоге src создайте две директории: main, test.
        File dirMain = new File(dirSrc.getPath(), "main");
        result.append(createDirectory(dirMain));

        File dirTest = new File(dirSrc.getPath(), "test");
        result.append(createDirectory(dirTest));

        //3. В подкаталоге main создайте два файла: Main.java, Utils.java.
        File fileMain = new File("D://netology/Games/src/main//Main.java");
        result.append(createFile(fileMain));

        File fileUtils = new File("D://netology/Games/src/main//Utils.java");
        result.append(createFile(fileUtils));

        //4. В каталог res создайте три директории: drawables, vectors, icons.
        File dirDrawables = new File(dirRes.getPath(), "drawables");
        result.append(createDirectory(dirDrawables));

        File dirVectors = new File(dirRes.getPath(), "vectors");
        result.append(createDirectory(dirVectors));

        File dirIcons = new File(dirRes.getPath(), "icons");
        result.append(createDirectory(dirIcons));

        //5. В директории temp создайте файл temp.txt.
        File fileTemp = new File("D://netology/Games/temp//temp.txt");
        result.append(createFile(fileTemp));

        //запись логов в файл
        try (FileOutputStream outputStream = new FileOutputStream("D://netology/Games/temp//temp.txt")) {
            byte[] bytes = result.toString().getBytes();
            outputStream.write(bytes, 0, bytes.length);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        //ВТОРОЕ ЗАДАНИЕ
        GameProgress gameProgress1 = new GameProgress(70, 5, 3, 15);
        GameProgress gameProgress2 = new GameProgress(80, 10, 15, 45.4);
        GameProgress gameProgress3 = new GameProgress(30, 41, 31, 85.8);
        saveGame("D://netology/Games/savegames//savegame1.txt", gameProgress1);
        saveGame("D://netology/Games/savegames//savegame2.txt", gameProgress2);
        saveGame("D://netology/Games/savegames//savegame3.txt", gameProgress3);
        //архивация
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream("D://netology/Games/savegames//zip_savegame.zip"));
             FileInputStream fis1 = new FileInputStream("D://netology/Games/savegames//savegame1.txt");
             FileInputStream fis2 = new FileInputStream("D://netology/Games/savegames//savegame2.txt");
             FileInputStream fis3 = new FileInputStream("D://netology/Games/savegames//savegame3.txt")) {
            ZipEntry entry1 = new ZipEntry("savegame1.txt");
            zout.putNextEntry(entry1);
            byte[] buffer1 = new byte[fis1.available()];
            fis1.read(buffer1);
            zout.write(buffer1);

            ZipEntry entry2 = new ZipEntry("savegame2.txt");
            zout.putNextEntry(entry2);
            byte[] buffer2 = new byte[fis2.available()];
            fis2.read(buffer2);
            zout.write(buffer2);

            ZipEntry entry3 = new ZipEntry("savegame3.txt");
            zout.putNextEntry(entry3);
            byte[] buffer3 = new byte[fis3.available()];
            fis3.read(buffer3);
            zout.write(buffer3);

            zout.closeEntry();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //УДАЛЕНИЕ
        delete("D://netology/Games/savegames//savegame1.txt");
        delete("D://netology/Games/savegames//savegame2.txt");
        delete("D://netology/Games/savegames//savegame3.txt");
    }

    public static String createDirectory(File dir) {
        if (dir.mkdir()) {
            return "Создана директория " + dir.getName() + " по пути " + dir.getPath() + "\n";
        } else {
            return "ERROR! Ошибка при создани директории " + dir.getName() + " по пути " + dir.getPath() + "\n";
        }
    }

    public static String createFile(File file) {
        try {
            if (file.createNewFile()) {
                return "Файл " + file.getName() + " был успешно создан по пути " + file.getPath() + "\n";
            }
        } catch (IOException e) {
            return "ERROR! Ошибка при создании файла " + file.getName() + " по пути " + file.getPath() + "\n";
        }
        return null;
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void delete(String filePath) {
        File file = new File(filePath);
        file.delete();
    }
}