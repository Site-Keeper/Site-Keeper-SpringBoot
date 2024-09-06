package com.riwi.sitekeeper.services.CRUD;

public interface ReadById <Entity, ID>{
    public Entity ReadByid(ID id);
}
