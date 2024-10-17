package com.edohan.fuse.mapper;

import java.util.*;

public interface CommonMapper {
    List<Map<String, Object>> codeList(String type);
    
    List<Map<String, Object>> areaCdList(String regionCode);
}
