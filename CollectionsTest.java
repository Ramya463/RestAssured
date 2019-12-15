import org.testng.annotations.Test;

import java.util.*;

public class CollectionsTest {

    //ArrayList
    /*
    contains duplicates
    maintains insertion order

     */
    @Test
    public static void arrayListTest(){
        List<String> topCompanies=new ArrayList<String>();
        topCompanies.add("Google");

        topCompanies.add("Infy");

        topCompanies.add("Oracle");

        topCompanies.add("HCL");
        System.out.println(topCompanies.isEmpty());

        for(int i=0;i<topCompanies.size();i++){
            System.out.println(topCompanies.get(i));
        }
        Iterator<String> companiesIterator=topCompanies.iterator();
        while (companiesIterator.hasNext()){
            System.out.println(companiesIterator.next());
        }


    }

    @Test
    public static void linkedListTest(){
        List<String> hunamSpecies=new LinkedList<String>();
        hunamSpecies.add("Homo Sapiens");
        hunamSpecies.add("Homo Erectus");
        hunamSpecies.add("Homo Neanderthalensis");
        hunamSpecies.add("Homo habilis");

        Iterator<String> humanspeciesiterator=hunamSpecies.iterator();
        while (humanspeciesiterator.hasNext()){
            System.out.println(humanspeciesiterator.next());
        }


    }
}
