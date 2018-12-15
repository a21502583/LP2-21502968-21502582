package pt.ulusofona.lp2.crazyChess;

public class TowerV extends CrazyPiece {
    public TowerV(String idPeca, String idTipoPeca, String idEquipa, String alcunha, int x, int y) {
        super(idPeca, idTipoPeca, idEquipa, alcunha, x, y);
    }

    public TowerV(int idPeca, int idTipoPeca, int idEquipa, String alcunha, int x, int y) {
        super(idPeca, idTipoPeca, idEquipa, alcunha, x, y);
    }

    public String getImagePNG() {
        if (idEquipa == 10){
            return "TorreVBlack.png";
        } else {
            return "TorreVWhite.png";
        }
    }
}
