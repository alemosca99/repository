package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//classe <<controller>>

public class ViewAdvice {

    //metodi
    public List<ViewAdviceBean> viewAllAdvice() throws SQLException, ClassNotFoundException, ItemNotFoundException{
        List<Advise> list=DAOAdvise.getDAOAdviseInstance().getAllAdvice();
        if(list.isEmpty()){
            throw new ItemNotFoundException();
        }
        List<ViewAdviceBean> lb = new ArrayList<>();
        for(Advise a : list){
            lb.add(new ViewAdviceBean(a.getId(), a.getUser().getName(),  a.getText()));
        }
        return lb;
    }
    public List<ViewAdviseResultBean> viewAdviceResults(ViewAdviseResultInputBean bean) throws SQLException, ClassNotFoundException, ItemNotFoundException{
        Advise a = DAOAdvise.getDAOAdviseInstance().getAdvise(bean.getUser(), bean.getId());
        if(a.getResult().isEmpty()){
            throw new ItemNotFoundException();
        }
        return calculateResult(a);
    }
    public List<ViewAdviceBean> viewAdviceByUser(String user) throws SQLException, ClassNotFoundException, ItemNotFoundException{
        List<Advise> list=DAOAdvise.getDAOAdviseInstance().getAdviceByUser(user);
        if(list.isEmpty()){
            throw new ItemNotFoundException();
        }
        List<ViewAdviceBean> lb = new ArrayList<>();
        for(Advise a : list){
            lb.add(new ViewAdviceBean(a.getId(), a.getUser().getName(),  a.getText()));
        }
        return lb;
    }
    public List<ViewAdviseResultBean> calculateResult(Advise a){
        List<ViewAdviseResultBean> list = new ArrayList<>();
        for(AdviseResult res : a.getResult()){
            insert(res.getVideogame(), list);
        }
        return list;
    }
    private void insert(Videogame v, List<ViewAdviseResultBean> list){
        for(ViewAdviseResultBean bean : list){
            if(v.getName().equals(bean.getVideogame())){
                bean.incrementAdvice();
                return;
            }
        }
        list.add(new ViewAdviseResultBean(v.getName()));
    }

}
