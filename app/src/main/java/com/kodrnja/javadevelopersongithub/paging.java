package com.kodrnja.javadevelopersongithub;

import com.kodrnja.javadevelopersongithub.Model.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iva Kodrnja on 30.4.2017..
 */

public class paging {


    public static final int ITEMS_PER_PAGE=10;

    public List<UserModel> generatePage(int currentPage, int totalPages,int TOTAL_NUM_ITEMS, List<UserModel> usermodellist){



        int ITEMS_REMAINED=TOTAL_NUM_ITEMS%ITEMS_PER_PAGE;
        int LAST_PAGE=TOTAL_NUM_ITEMS/ITEMS_PER_PAGE;
        int startItem = currentPage*ITEMS_PER_PAGE;
        int numofData = ITEMS_PER_PAGE;

        ArrayList<UserModel> pageData = new ArrayList<>();

        if(currentPage==LAST_PAGE && ITEMS_REMAINED>0){

            for(int i=startItem;i<startItem+ITEMS_REMAINED;i++){
                pageData.add(usermodellist.get(i));
            }
        }else{
            for(int i=startItem;i<startItem+numofData;i++){
                pageData.add(usermodellist.get(i));
            }
        }

        return pageData;

    }





}
