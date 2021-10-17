package cpen221.mp1;

public class TextFormatters {

    public String formatDocument(String seed){
        String formattedDoc = seed;

        while (formattedDoc.contains("  ")){
            formattedDoc = formattedDoc.replaceAll("  "," ");
        }
        while (formattedDoc.contains("\n")){
            formattedDoc = formattedDoc.replaceAll("\n","");
        }
        while (formattedDoc.contains("\t")){
            formattedDoc = formattedDoc.replaceAll("\t"," ");
        }

        return formattedDoc;
    }
}
