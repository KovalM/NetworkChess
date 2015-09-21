package chessmodel.piecemodel;

import chessmodel.DeskModel;
import chessmodel.PositionWithPiece;
import chessmodel.CheckerboardPosition;

import java.util.ArrayList;
import java.util.List;

public class QueenModel extends PieceModel {
    public QueenModel(String color, CheckerboardPosition piecePosition) {
        super(color, piecePosition);
    }

    @Override
    public List<PositionWithPiece> getAllCandidate(DeskModel deskModel) {
        List<PositionWithPiece> allCandidate = new ArrayList<>();
        passDirection(1, 1, deskModel, allCandidate);
        passDirection(1, -1, deskModel, allCandidate);
        passDirection(-1, 1, deskModel, allCandidate);
        passDirection(-1, -1, deskModel, allCandidate);
        passDirection(1, 0, deskModel, allCandidate);
        passDirection(0, 1, deskModel, allCandidate);
        passDirection(-1, 0, deskModel, allCandidate);
        passDirection(0, -1, deskModel, allCandidate);
        return allCandidate;
    }

    @Override
    public List<PositionWithPiece> getAtackPositions(DeskModel deskModel) {
        return getAllCandidate(deskModel);
    }
}
