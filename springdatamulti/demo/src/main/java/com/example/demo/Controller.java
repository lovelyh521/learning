package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类描述：提供接口
 *
 * @author
 */
@RestController
public class Controller
{

    @Autowired
    private GJSHRepository shRepository;

    @Autowired
    private GJJSRepository jsRepository;

    //上海光交新增
    @RequestMapping(value = "/devicegjsh", method = RequestMethod.POST)
    public @ResponseBody
    List addGJ(@RequestBody Map<String, String> payload)
    {
        DeviceGJSH gjsh = new DeviceGJSH();
        if(payload == null || payload.isEmpty()){
            return new ArrayList();
        }
        gjsh.setGjSHName(payload.getOrDefault("device_connect_gj_sh","上海光交分表"));
        gjsh.setConnect(payload.getOrDefault("device_connect","连接设备表"));
        gjsh.setDeviceName(payload.getOrDefault("device","设备表"));
        gjsh.setGjName(payload.getOrDefault("device_connect_gj","光交集团表"));
        shRepository.save(gjsh);

        //保存后查询下所有数据
        List<DeviceGJSH> result = shRepository.findAll();
        return result;
    }

    //江苏光交新增
    @RequestMapping(value = "/devicegjjs", method = RequestMethod.POST)
    public @ResponseBody
    List addGJJS(@RequestBody Map<String, String> payload)
    {
        DeviceGJJS gjjs = new DeviceGJJS();
        if(payload == null || payload.isEmpty()){
            return new ArrayList();
        }
        gjjs.setGjJSName(payload.getOrDefault("device_connect_gj_js","江苏光交分表"));
        gjjs.setConnect(payload.getOrDefault("device_connect","连接设备表-江苏"));
        gjjs.setDeviceName(payload.getOrDefault("device","设备表-江苏"));
        gjjs.setGjName(payload.getOrDefault("device_connect_gj","光交集团表-江苏"));
        jsRepository.save(gjjs);

        //保存后查询下所有数据
        List<DeviceGJJS> result = jsRepository.findAll();
        return result;
    }


}
