package cpen221.mp1.similarity;

import cpen221.mp1.Document;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class GroupingDocuments {

    /* ------- Task 5 ------- */

    /**
     * Group documents by similarity
     * @param allDocuments the set of all documents to be considered,
     *                     is not null
     * @param numberOfGroups the number of document groups to be generated
     * @return groups of documents, where each group (set) contains similar
     * documents following this rule: if D_i is in P_x, and P_x contains at
     * least one other document, then P_x contains some other D_j such that
     * the divergence between D_i and D_j is smaller than (or at most equal
     * to) the divergence between D_i and any document that is not in P_x.
     */
    public static Set<Set<Document>> groupBySimilarity(Set<Document> allDocuments, int numberOfGroups) {

        List<ArrayList<Double>> divergenceList = new ArrayList<ArrayList<Double>>();
        List<Document> docList = new ArrayList<Document>(allDocuments);
        List<ArrayList<Document>> partitionList = new ArrayList<ArrayList<Document>>();

        for (int i = 0; i < allDocuments.size() - 1; i++){
            ArrayList<Double> tempDivergences = new ArrayList<Double>();
            for (int j = i + 1; j < allDocuments.size(); j++){
                DocumentSimilarity b = new DocumentSimilarity(); //Should probably change to static
                tempDivergences.add(b.documentDivergence(docList.get(i), docList.get(j))); //Switch in - make document similarity a static class then switch in DocumentSimilarity.Divergence(docList.get(i),docList.get(j)
            }
            divergenceList.add(tempDivergences);
        }
        for (int i = 0; i < docList.size(); i++){
            ArrayList<Document> temp = new ArrayList<Document>();
            temp.add(docList.get(i));
            partitionList.add(temp);
        }

        ArrayList<ArrayList<Integer>> sortedIndexes = new ArrayList<ArrayList<Integer>>();
        sortedIndexes = indexSort(divergenceList);

        int index1, index2;
        int counter = 0;
        while (partitionList.size() > numberOfGroups){
            index1 = sortedIndexes.get(counter).get(0);
            index2 = sortedIndexes.get(counter).get(1);
            merger(partitionList, docList.get(index1), docList.get(index2+index1+1));
            counter++;

        }
        Set<Set<Document>> finalPartition = new HashSet<Set<Document>>();
        for(int i = 0 ; i < partitionList.size(); i++){

            Set<Document> set = new HashSet<Document>(partitionList.get(i));
            finalPartition.add(set);
        }

        return finalPartition;
    }

    /**
     * Merge two lists in an arraylist
     * @param partDoc a List containing Lists of Documents
     * @param document1 a Document
     * @param document2 a Document
     * Merge two lists in an arraylist
     */
    private static void merger(List<ArrayList<Document>> partDoc, Document document1, Document document2){
        int index1 = -1, index2 = -1;

        for (int i = 0; i < partDoc.size(); i++){
            if (partDoc.get(i).contains(document1)){
                index1 = i;
            }
            if (partDoc.get(i).contains(document2)){
                index2 = i;
            }
        }

        if (index1 != index2) {
            partDoc.get(index1).addAll(partDoc.get(index2));
            partDoc.remove(index2);
        }
    }

    /**
     * Sort indexes of the double arraylist based on the smallest to largest divergence
     * @param divergences  List that is non-null and contains Lists that are non null
     * @return List containing sub-Lists of element indexes. Sub-Lists elements are in an order
     * reflective of the ascending order of the elements in the inputted list.
     */
    private static ArrayList<ArrayList<Integer>> indexSort(List<ArrayList<Double>> divergences){
        ArrayList<ArrayList<Integer>> indexes = new ArrayList<ArrayList<Integer>>();
        ArrayList<Double> aCompress = new ArrayList<Double>();

        for (int i = 0; i < divergences.size(); i++){
            aCompress.addAll(divergences.get(i));
            for(int j = 0; j < divergences.get(i).size(); j++){
                ArrayList<Integer> element = new ArrayList<Integer>();
                element.add(i);
                element.add(j);
                indexes.add(element);
            }
        }
        for (int j = 0; j < aCompress.size() - 1; j++){
            for(int k = 1 + j; k < aCompress.size(); k++){
                if(aCompress.get(j) > aCompress.get(k)){
                    Collections.swap(aCompress, j, k);
                    Collections.swap(indexes, j , k);
                }
            }
        }

        return indexes;
    }
}
