package com.github.louchen.zoom.controller.admin;

import com.github.louchen.zoom.api.store.model.Store;
import com.github.louchen.zoom.api.store.service.StoreService;
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
 * 控制器 - 店铺
 *
 * @author louchen
 */
@RestController
@RequestMapping("/api/admin/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(method = RequestMethod.GET)
    public Page<Store> getList(String name, Integer page, Integer size) {
        PageRequest pageRequest = new PageRequest(page, size);
        Page<Store> stores = storeService.findByNameLike(name, pageRequest);
        return stores;
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Store get(@PathVariable Long id) {
        return storeService.find(id);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(method = RequestMethod.POST)
    public Store add(@RequestBody Store store) {
        return storeService.save(store);
    }


    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Store update(@RequestBody Store store) {
        return storeService.update(store);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        storeService.delete(id);
    }

}
