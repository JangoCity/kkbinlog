package cn.keking.project.binlogdistributor.app.web.controller;

import cn.keking.project.binlogdistributor.app.config.BinaryLogConfig;
import cn.keking.project.binlogdistributor.app.model.ServiceStatus;
import cn.keking.project.binlogdistributor.app.model.vo.BinaryLogConfigVO;
import cn.keking.project.binlogdistributor.app.service.DistributorService;
import cn.keking.project.binlogdistributor.app.util.Result;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wanglaomo
 * @since 2019/6/10
 **/
@RestController
@RequestMapping("/datasource")
public class DataSourceController {

    private final DistributorService distributorService;

    public DataSourceController(@Qualifier DistributorService distributorService) {
        this.distributorService = distributorService;
    }

    @GetMapping("/list")
    public List<BinaryLogConfigVO> datasourceConfigs() {

        return distributorService.getAllConfigs();
    }

    @PostMapping("/persist")
    public Result persistDatasourceConfig(@RequestBody BinaryLogConfig config) {

        if(distributorService.persistDatasourceConfig(config)) {
            return new Result(Result.SUCCESS, "添加数据源成功");
        } else {
            return new Result(Result.ERROR, "添加数据源失败，命名空间已存在");
        }
    }

    @PostMapping("/remove")
    public Result removeDatasourceConfig(@RequestBody JSONObject JsonObject) {

        if(distributorService.removeDatasourceConfig(JsonObject.getString("namespace"))) {
            return new Result(Result.SUCCESS, "移除数据源成功");
        } else {
            return new Result(Result.ERROR, "移除数据源失败");
        }
    }

    @PostMapping("/start")
    public Result startDatasource(@RequestBody JSONObject jsonObject) {

        String namespace = jsonObject.getString("namespace");
        String delegatedIp = jsonObject.getString("delegatedIp");

        if(distributorService.startDatasource(namespace, delegatedIp)) {
            return new Result(Result.SUCCESS, "发送开启数据源监听命令成功");
        } else {
            return new Result(Result.ERROR, "发送开启数据源监听命令失败");
        }
    }

    @PostMapping("/stop")
    public Result stopDatasource(@RequestBody JSONObject JsonObject) {

        if(distributorService.stopDatasource(JsonObject.getString("namespace"))) {
            return new Result(Result.SUCCESS, "发送关闭数据源监听命令成功");
        } else {
            return new Result(Result.ERROR, "发送关闭数据源监听命令失败");
        }
    }

    @GetMapping("/service-status")
    public List<ServiceStatus> getServiceStatus() {
        return distributorService.getServiceStatus();
    }
}
