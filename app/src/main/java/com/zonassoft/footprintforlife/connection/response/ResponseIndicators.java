package com.zonassoft.footprintforlife.connection.response;

import com.zonassoft.footprintforlife.model.HIndicator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ResponseIndicators implements Serializable {
    public int status = -1;
    public List<HIndicator> result = new ArrayList<>();
}
