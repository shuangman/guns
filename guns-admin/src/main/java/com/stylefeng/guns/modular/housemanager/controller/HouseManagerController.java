package com.stylefeng.guns.modular.housemanager.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.util.ToolUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.system.model.HouseManager;
import com.stylefeng.guns.modular.housemanager.service.IHouseManagerService;

/**
 * 房屋管理控制器
 *
 * @author fengshuonan
 * @Date 2019-04-10 10:18:33
 */
@Controller
@RequestMapping("/houseManager")
public class HouseManagerController extends BaseController {

    private String PREFIX = "/housemanager/houseManager/";

    @Autowired
    private IHouseManagerService houseManagerService;

    /**
     * 跳转到房屋管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "houseManager.html";
    }

    /**
     * 跳转到添加房屋管理
     */
    @RequestMapping("/houseManager_add")
    public String houseManagerAdd() {
        return PREFIX + "houseManager_add.html";
    }

    /**
     * 跳转到修改房屋管理
     */
    @RequestMapping("/houseManager_update/{houseManagerId}")
    public String houseManagerUpdate(@PathVariable Integer houseManagerId, Model model) {
        HouseManager houseManager = houseManagerService.selectById(houseManagerId);
        model.addAttribute("item", houseManager);
        LogObjectHolder.me().set(houseManager);
        return PREFIX + "houseManager_edit.html";
    }

    /**
     * 获取房屋管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        //判断condition是否有值
        if (ToolUtil.isEmpty(condition)) {
            //如果没有值，则表示查询全部
            return houseManagerService.selectList(null);
        } else {
            //如果有值，则认为是按业务名称进行模糊查询
            EntityWrapper<HouseManager> entityWrapper = new EntityWrapper<>();
            Wrapper<HouseManager> wrapper = entityWrapper.like("house_user", condition);
            return houseManagerService.selectList(wrapper);
        }
    }

    /**
     * 新增房屋管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(HouseManager houseManager) {
        houseManagerService.insert(houseManager);
        return SUCCESS_TIP;
    }

    /**
     * 删除房屋管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer houseManagerId) {
        houseManagerService.deleteById(houseManagerId);
        return SUCCESS_TIP;
    }

    /**
     * 修改房屋管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(HouseManager houseManager) {
        if (ToolUtil.isEmpty(houseManager) || houseManager.getId() == null) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        houseManagerService.updateById(houseManager);
        return SUCCESS_TIP;
    }

    /**
     * 房屋管理详情
     */
    @RequestMapping(value = "/detail/{houseManagerId}")
    @ResponseBody
    public Object detail(@PathVariable("houseManagerId") Integer houseManagerId) {
        return houseManagerService.selectById(houseManagerId);
    }
}
