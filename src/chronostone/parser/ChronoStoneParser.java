/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronostone.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Adrian
 */
public class ChronoStoneParser {

    static String TENMA = "http://inazuma-eleven.wikia.com/wiki/Matsukaze_Tenma";
    static String root = "http://inazuma-eleven.wikia.com/wiki/";
    static Character tenmakun = new Character();
    static String MF = "Midfielder";
    static String FW = "Forward";
    static String DF = "Defender";
    static String GK = "Goalkeeper";
    static String ONE_STATS = "</p></div><div class=\"tabbertab\" title=\"Inazuma Eleven GO 2: Chrono Stone\"><p>";
    static String TWO_STATS = "<div class=\"tabber\"><div class=\"tabbertab\" title=\"Inazuma Eleven GO 2: Chrono Stone\"><p>";
    static String AURA_HTML = "t Lvl.";
    static int xls_index = 0;
    static String gp = "GP";
    public static final String TP = "TP";
    public static final String KICK = "Kick";
    public static final String LUCKY = "Lucky";
    public static final String STAMINA = "Stamina";
    public static final String SPEED = "Speed";
    public static final String TECHNIQUE = "Technique";
    public static final String A_CATCH = "Catch";
    public static final String BLOCK = "Block";
    public static final String DRIBBLING = "Dribbling";

    public static void main(String[] args) throws FileNotFoundException, IOException {

        String rutaArchivo = System.getProperty("user.home") + "/ChronoStoneSheet.xls";
        File archivoXLS = new File(rutaArchivo);
        if (archivoXLS.exists() == false) {
            try {
                archivoXLS.createNewFile();
            } catch (IOException ex) {
                System.err.println("Error on creating XLS");
            }
        }

        Workbook libro = new HSSFWorkbook();
        FileOutputStream archivo = new FileOutputStream(archivoXLS);
        Sheet hoja = libro.createSheet("Chrono Stone Sheet");
        /**
         * Codigo a remover o investigar como hacerlo funcionar de verdad
         */
        int check = check_created(hoja);
        if (check == xls_index) {
            inicializar_celdas(hoja);
        } else {
            xls_index = check + 1;
        }
        String name = "";
        System.out.println("Archivo creado: "+ rutaArchivo);
        System.out.println("Introduzca nombre");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        name = br.readLine();
        while (!name.equalsIgnoreCase("exit")) {
            try {

                tenmakun = new Character(name);
                name = name.replace(" ", "_");
                URLConnection connection = null;
                connection = new URL(root + name).openConnection();
                BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while ((line = bf.readLine()) != null) {
                    if (line.contains(ONE_STATS) || line.contains(TWO_STATS) || line.contains(AURA_HTML)) {
                        //Primero los stats
                        List<String> stats = new ArrayList<>();
                        for (int index = 0; index < 13; index++) {
                            stats.add(bf.readLine());
                        }
                        add_stats(stats);
                        //Y ahora a por las habilidades
                        //add_hissatsu(stats);
                    } else if (line.contains("<a href=\"/wiki/Category:Midfielders\"")) {
                        tenmakun.setPosition(MF);
                    } else if (line.contains("<a href=\"/wiki/Category:Forwards\"")) {
                        tenmakun.setPosition(FW);
                    } else if (line.contains("<a href=\"/wiki/Category:Defenders\"")) {
                        tenmakun.setPosition(DF);
                    } else if (line.contains("<a href=\"/wiki/Category:Goalkeepers\"")) {
                        tenmakun.setPosition(GK);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println(tenmakun.print_stats());
            add_character_sheet(hoja);
            System.out.println("Personaje añadido al fichero");
            System.out.println("Introduzca nombre");
            name = br.readLine();
        }
        libro.write(archivo);
        archivo.close();
        libro.close();
    }

    private static void add_stats(List<String> stats) throws Exception {
        for (String stat : stats) {
            stat = stat.replace("<ul>", "");
            stat = stat.replace("<li>", "");
            stat = stat.replace("<b>", "");
            stat = stat.replace("</b>", "");
            stat = stat.replace("</li>", "");
            stat = stat.replace(" ", "");

            if (stat.contains(gp)) {
                tenmakun.setGp(stat.substring(3));
                continue;
            }
            if (stat.contains(TP)) {
                tenmakun.setTp(stat.substring(3));
                continue;
            }
            if (stat.contains(KICK) && stat.length() < 10) {
                tenmakun.setKick(stat.substring(5));
                continue;
            }
            if (stat.contains(DRIBBLING)) {
                tenmakun.setDribbling(stat.substring(10));
                continue;
            }
            if (stat.contains(BLOCK)) {
            	if(stat.length() > 8) {
                tenmakun.setBlock(stat.substring(6,9));
                continue;
            	}else {
            		tenmakun.setBlock(stat.substring(6,8));
                    continue;
            	}
            }
            if (stat.contains(A_CATCH)) {
                tenmakun.setCatch_keeper(stat.substring(6));
                continue;
            }
            if (stat.contains(TECHNIQUE)) {
                tenmakun.setTechnique(stat.substring(10));
                continue;
            }
            if (stat.contains(SPEED)) {
                tenmakun.setSpeed(stat.substring(6));
                continue;
            }
            if (stat.contains(STAMINA)) {
                tenmakun.setStamina(stat.substring(8));
                continue;
            }
            if (stat.contains(LUCKY)) {
                tenmakun.setLucky(stat.substring(6));
            }
        }
    }

    private static void add_hissatsu(List<String> hissatsus) {
        for (String hissatsu : hissatsus) {
            if (is_hissatsu(hissatsu)) {
                String[] segments = hissatsu.split("title=");
                String to_add = segments[segments.length - 1];
                to_add = to_add.replace("\"", "");
                to_add = to_add.replace("</a></b>", "");
                segments = to_add.split(">");
                to_add = segments[0];
                tenmakun.addHissatsu(to_add);
            }
        }
    }

    private static boolean is_hissatsu(String hissatsu) {
        boolean response = true;
        if (hissatsu.contains("GP") || hissatsu.contains("TP") || hissatsu.contains("Kick") || hissatsu.contains("Dribbling") || hissatsu.contains("Block") || hissatsu.contains("Catch") || hissatsu.contains("Technique") || hissatsu.contains("Speed") || hissatsu.contains("Stamina") || hissatsu.contains("Lucky")) {
            response = false;
        }
        return response;
    }

    private static void inicializar_celdas(Sheet hoja) {
        Row fila = hoja.createRow(xls_index);
        xls_index++;
        Cell celda = fila.createCell(0);
        celda.setCellValue("Name");
        celda = fila.createCell(1);
        celda.setCellValue("Position");
        celda = fila.createCell(2);
        celda.setCellValue(gp);
        celda = fila.createCell(3);
        celda.setCellValue(TP);
        celda = fila.createCell(4);
        celda.setCellValue(KICK);
        celda = fila.createCell(5);
        celda.setCellValue(DRIBBLING);
        celda = fila.createCell(6);
        celda.setCellValue(BLOCK);
        celda = fila.createCell(7);
        celda.setCellValue(A_CATCH);
        celda = fila.createCell(8);
        celda.setCellValue(TECHNIQUE);
        celda = fila.createCell(9);
        celda.setCellValue(SPEED);
        celda = fila.createCell(10);
        celda.setCellValue(STAMINA);
        celda = fila.createCell(11);
        celda.setCellValue(LUCKY);
    }

    private static int check_created(Sheet hoja) {
        int ret = hoja.getLastRowNum();
        return ret;
    }

    private static void add_character_sheet(Sheet hoja) {
        Row fila = hoja.createRow(xls_index);
        xls_index++;
        Cell celda = fila.createCell(0);
        celda.setCellValue(tenmakun.getName());
        celda = fila.createCell(1);
        celda.setCellValue(tenmakun.getPosition());
        celda = fila.createCell(2);
        celda.setCellValue(tenmakun.getGp());
        celda = fila.createCell(3);
        celda.setCellValue(tenmakun.getTp());
        celda = fila.createCell(4);
        celda.setCellValue(tenmakun.getKick());
        celda = fila.createCell(5);
        celda.setCellValue(tenmakun.getDribbling());
        celda = fila.createCell(6);
        celda.setCellValue(tenmakun.getBlock());
        celda = fila.createCell(7);
        celda.setCellValue(tenmakun.getCatch_keeper());
        celda = fila.createCell(8);
        celda.setCellValue(tenmakun.getTechnique());
        celda = fila.createCell(9);
        celda.setCellValue(tenmakun.getSpeed());
        celda = fila.createCell(10);
        celda.setCellValue(tenmakun.getStamina());
        celda = fila.createCell(11);
        celda.setCellValue(tenmakun.getLucky());
    }
}
