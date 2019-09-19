package com.fengshihao.album.api;

import com.fengshihao.xframe.logic.ItemSelection;

public interface IAlbumProjectListener extends IAlbumDataLoaderListener,
    ItemSelection.Listener<Integer> {
}
