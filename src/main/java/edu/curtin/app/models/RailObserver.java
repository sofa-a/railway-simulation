package edu.curtin.app.models;

/*
 * Observer interface for updating information relating to railways.
 */

import java.util.List;

public interface RailObserver {
    void updateRails(List<Railway> railList);
}
