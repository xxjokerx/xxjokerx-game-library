package org.motoc.gamelibrary.repository.fragment;

import org.motoc.gamelibrary.model.GameCopy;
import org.motoc.gamelibrary.model.Publisher;
import org.motoc.gamelibrary.model.Seller;

public interface GameCopyFragmentRepository {
    GameCopy addSeller(GameCopy copy, Seller seller);

    GameCopy removeSeller(GameCopy copy, Seller seller);

    GameCopy addPublisher(GameCopy copy, Publisher publisher);

    GameCopy removePublisher(GameCopy copy, Publisher publisher);
}