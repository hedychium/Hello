package com.jl.crawl.link;

public interface LinkFilter {
    public boolean accept(String url);
}