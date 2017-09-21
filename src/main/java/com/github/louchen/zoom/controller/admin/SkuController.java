package com.github.louchen.zoom.controller.admin;

import com.github.louchen.zoom.api.sku.model.Sku;
import com.github.louchen.zoom.api.sku.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器 - sku
 *
 * @author louchen
 */
@RestController
@RequestMapping("/api/admin/sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(method = RequestMethod.GET)
    public Page<Sku> getList(String name, Integer page, Integer size) {
        PageRequest pageRequest = new PageRequest(page, size);
        Page<Sku> skus = skuService.findByNameLike(name, pageRequest);
        return skus;
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Sku get(@PathVariable Long id) {
        return skuService.find(id);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(method = RequestMethod.POST)
    public Sku add(@RequestBody Sku sku) {
        return skuService.save(sku);
    }


    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Sku update(@RequestBody Sku sku) {
        return skuService.update(sku);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        skuService.delete(id);
    }

}
