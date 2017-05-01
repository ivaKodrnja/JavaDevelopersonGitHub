package com.kodrnja.javadevelopersongithub;

import com.kodrnja.javadevelopersongithub.Model.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iva Kodrnja on 30.4.2017..
 */

public class paging {

    public List<UserModel> generatePage(int currentPage,int totalPages, List<UserModel> usermodellist){

        int startItem = currentPage*11;


        List<UserModel> pageData = new ArrayList<>();

        if(currentPage==totalPages/10 && totalPages%10>0){

            for(int i=startItem;i<startItem+totalPages%10;i++){
                pageData.add(usermodellist.get(i));
            }
        }else{
            for(int i=startItem;i<startItem+9;i++){
                pageData.add(usermodellist.get(i));
            }
        }

        return pageData;

    }





}
