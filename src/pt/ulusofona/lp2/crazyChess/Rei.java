package pt.ulusofona.lp2.crazyChess;

public class Rei extends CrazyPiece {

    public Rei(String idPeca, String idTipoPeca, String idEquipa, String alcunha, int x, int y) {
        super(idPeca, idTipoPeca, idEquipa, alcunha, x, y);
        tipoeDePeca = "Rei";
        valorRelativo = "(infinito)";
        valorRelativoParaCalculo = 1000;
    }

    public Rei(int idPeca, int idTipoPeca, int idEquipa, String alcunha, int x, int y) {
        super(idPeca, idTipoPeca, idEquipa, alcunha, x, y);
        tipoeDePeca = "Rei";
        valorRelativo = "(infinito)";
        valorRelativoParaCalculo = 1000;
    }

    @Override
    public String getImagePNG() {
        if (idEquipa == 10){
            return "KingBlack.png";
        } else {
            return "KingWhite.png";
        }
    }

    @Override
    public boolean movimento(int xO, int yO, int xD, int yD, CrazyPiece crazy) {
        int deltaX = Math.abs(xD - xO);
        int deltaY = Math.abs(yD - yO);

        if (deltaX > 1 || deltaY > 1){
            return false;
        }

        if (deltaX == 0 || deltaY == 0){
            return false;
        }

            if(!findFriend(xD, yD, idPeca, idEquipa)){
                return false;
            }

            findCapture(xD, yD, idPeca, idEquipa);
            coordenadaX = xD;
            coordenadaY = yD;

            return true; //TODO: Até contrário funciona.

        

    }

    @Override
    public boolean movimentoPrevisao(int xO, int yO, int xD, int yD, CrazyPiece crazy) {
        int deltaX = Math.abs(xD - xO);
        int deltaY = Math.abs(yD - yO);

        if (deltaX > 1 || deltaY > 1){
            return false;
        }
        
        if (deltaX == 0 || deltaY == 0){
            return false;
        }

            if(!findFriend(xD, yD, idPeca, idEquipa)){
                return false;
            }

            return true; //TODO: Até contrário funciona.

    }

}
