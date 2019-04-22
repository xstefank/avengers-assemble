package org.assemble.model;

import java.util.List;

public class DataTable {

    private long draw;
    private long recordsTotal;
    private long recordsFiltered;
    private List<Avenger> data;
    private String error;

    public long getDraw() {
        return draw;
    }

    public void setDraw(long draw) {
        this.draw = draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<Avenger> getData() {
        return data;
    }

    public void setData(List<Avenger> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "DataTable{" +
            "draw=" + draw +
            ", recordsTotal=" + recordsTotal +
            ", recordsFiltered=" + recordsFiltered +
            ", data=" + data +
            ", error='" + error + '\'' +
            '}';
    }
}
