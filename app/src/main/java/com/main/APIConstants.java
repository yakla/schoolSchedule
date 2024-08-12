package com.main;

import java.util.Map;

public class APIConstants {
    public String responseJson;
    public boolean isOffline = false;
    //    public String[] removeWords = {
//            "<td class=\"TTCell\" nowrap>",
//            "</td>",
//            "<td class=\"TTCell\" nowrap=\"nowrap\">",
//            "<b>",
//            "&",
//            "nbsp;&nbsp",
//            "<div/>",
//            "br",
//            "tr bgcolor=\"#ffffff\" valign=\"top\"",
//            "\t\t\t<td valign=\"middle\" class=\"CName\"",
//            "div class=\"TTLesson\"",
//            "nbsp;",
//            "&",
//            "<",
//            ">",
//            "/ n sp;n sp;",
//            "r" ,
//            "class=\"TTLesson\"",
//            "quot;",
//            "\t\tt  gcolo=\"#ffffff\" valign=\"top\"",
//            "/span",
//    };
    public Map<String, String> menuOptions = Map.of("Schedule", "dnn$ctr7126$TimeTableView$btnTimeTable", "ScheduleUpdated", "dnn$ctr7126$TimeTableView$btnChangesTable");

    public enum option {
        Schedule,
        ScheduleUpdated;
    }

    public String[] Classes = {
            "C9_1",
            "C9_2",
            "C9_3",
            "C9_4",
            "C9_5",
            "C9_6",
            "C9_7",
            "C9_8",
            "C9_9",
            "C9_10",
            "C9_11",
            "C9_12",
            "C9_13",
            "C9_14",
            "C9_15",
            "C10_1",
            "C10_2",
            "C10_3",
            "C10_4",
            "C10_5",
            "C10_6",
            "C10_7",
            "C10_8",
            "C10_9",
            "C10_10",
            "C10_11",
            "C10_12",
            "C10_13",
            "C10_14",
            "C10_15",
            "C11_1",
            "C11_2",
            "C11_3",
            "C11_4",
            "C11_5",
            "C11_6",
            "C11_7",
            "C11_8",
            "C11_9",
            "C11_10",
            "C11_11",
            "C11_12",
            "C11_13",
            "C11_14",
            "C11_15",
            "C12_1",
            "C12_2",
            "C12_3",
            "C12_4",
            "C12_5",
            "C12_6",
            "C12_7",
            "C12_8",
            "C12_9",
            "C12_10",
            "C12_11",
            "C12_12",
            "C12_13",
            "C12_14",
            "C12_15"
    };


    public int[] classNumbers = {
            172,
            173,
            174,
            175,
            176,
            177,
            178,
            179,
            180,
            181,
            182,
            183,
            184,
            185,
            186,
            128,
            129,
            130,
            131,
            132,
            133,
            134,
            135,
            136,
            137,
            138,
            139,
            140,
            126,
            171,
            141,
            142,
            143,
            144,
            145,
            146,
            147,
            148,
            149,
            150,
            151,
            152,
            153,
            154,
            187,
            156,
            157,
            158,
            159,
            160,
            161,
            162,
            163,
            164,
            165,
            166,
            167,
            168,
            169,
            188,
    };


}
