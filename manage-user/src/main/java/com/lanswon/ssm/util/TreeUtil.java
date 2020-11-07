package com.lanswon.ssm.util;

import com.lanswon.ssm.domain.vo.AppTreeVo;
import com.lanswon.ssm.domain.vo.MenuTreeVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/7/7 10:10
 */
public class TreeUtil {

    private static List<MenuTreeVo> all = null;

    public static List<MenuTreeVo> toTree(List<MenuTreeVo> list){
        all = new ArrayList<>(list);
        //拿到所有的顶级菜单
        List<MenuTreeVo> roots = new ArrayList<>();

        for(MenuTreeVo menuTreeVo :list){
            if( menuTreeVo.getFjqxbh() == null || menuTreeVo.getFjqxbh() == 0 ){
                roots.add(menuTreeVo);
            }
        }
        all.removeAll(roots);

        for(MenuTreeVo menuTreeVo :roots){
            menuTreeVo.setChildren(getCurrentNodeChildren(menuTreeVo));
        }
        return roots;
    }

    private static List<MenuTreeVo> getCurrentNodeChildren(MenuTreeVo parent){
        //TODO 判断当前节点是否有子节点，没有则创建一个空长度的List 有就使用之前已有的所有子节点
        List<MenuTreeVo> childList = parent.getChildren() == null ?new ArrayList<>() : parent.getChildren();

        //TODO 从待用菜单列表中找到当前节点的所有子节点
        for(MenuTreeVo child :all){
            if(parent.getQxbh() == child.getFjqxbh()){
                childList.add(child);
            }
        }
        all.removeAll(childList);

        for(MenuTreeVo menuTreeVo : childList){
            menuTreeVo.setChildren(getCurrentNodeChildren(menuTreeVo));
        }
        return childList;
    }

    //TODO 组织树

    private static List<AppTreeVo> allApp = null;

    public static List<AppTreeVo> toAppTree(List<AppTreeVo> list){
        allApp = new ArrayList<>(list);
        //拿到所有的顶级菜单
        List<AppTreeVo> roots = new ArrayList<>();

        for(AppTreeVo appTreeVo :list){
//            if(appTreeVo.getFjzzbm().equals(appTreeVo.getZzbm())){
//                roots.add(appTreeVo);
//            }
            if(appTreeVo.getFjzzbm() == null){
                roots.add(appTreeVo);
            }
        }
        allApp.removeAll(roots);

        for(AppTreeVo appTreeVo :roots){
            appTreeVo.setChildren(getCurrentNodeChildren(appTreeVo));
        }
        return roots;
    }

    private static List<AppTreeVo> getCurrentNodeChildren(AppTreeVo parent){
        //TODO 判断当前节点是否有子节点，没有则创建一个空长度的List 有就使用之前已有的所有子节点
        List<AppTreeVo> childList = parent.getChildren() == null ?new ArrayList<>() : parent.getChildren();

        //TODO 从待用菜单列表中找到当前节点的所有子节点
        for(AppTreeVo child :allApp){
            if(parent.getZzbm().equals(child.getFjzzbm())){
                childList.add(child);
            }
        }
        allApp.removeAll(childList);

        for(AppTreeVo appTreeVo : childList){
            appTreeVo.setChildren(getCurrentNodeChildren(appTreeVo));
        }
        return childList;
    }

}
