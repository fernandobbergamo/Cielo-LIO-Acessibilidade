package br.com.qrcode.util;

import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import br.com.qrcode.qrcode.GerarQrCode;

public class ConverterData {

    private static String dataAux, horarioAux;
    private static String[] data, horario;
    private static String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

    /**
     * Retorna somente os dias
     * @param time Data
     * @return  data[0] - ano
     *          data[1] - mes
     *          data[2] - dia
     */
    public static String[] retornaDias(String time){
        String[] split = time.split("T");
        dataAux = split[0];
        split = split[1].split("Z");
        data = dataAux.split("-");
        return data;
    }

    /**
     * Retorna somente o horário
     * @param time Data
     * @return  horario[0] - horas
     *          horario[1] - minutos
     *          horario[2] - segundos
     */
    public static String[] retornaHorario(String time){
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.HOUR_OF_DAY, -2);
        String[] split = time.split("T");
        dataAux = split[0];
        split = split[1].split("Z");
        horarioAux = split[0];
        horario = horarioAux.split(":");
        return horario;
    }

    public static String retornaDataFormatada(String time){
        String[] split = time.split("T");
        dataAux = split[0];
        split = split[1].split("Z");
        horarioAux = split[0];
        data = dataAux.split("-");
        horario = horarioAux.split(":");
        return data[2]+" de "+meses[Integer.parseInt(data[1])-1]+" às "+(Integer.valueOf(horario[0])-2)+" horas e "+horario[1]+" minutos ";
    }

    public static String retornaDataComBarras(String time){
        String[] split = time.split("T");
        dataAux = split[0];
        split = split[1].split("Z");
        horarioAux = split[0];
        data = dataAux.split("-");
        horario = horarioAux.split(":");
        return data[2]+"/"+data[1]+"/"+data[0];
    }
}
