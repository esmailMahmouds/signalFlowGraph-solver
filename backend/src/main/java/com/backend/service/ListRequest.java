package com.backend.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListRequest {
    private List<ArrayList<String>> list;

    public ListRequest() {
    }

    public List<ArrayList<String>> getList() {
        return list;
    }

    public void setList(List<ArrayList<String>> list) {
        this.list = list;
    }
}
