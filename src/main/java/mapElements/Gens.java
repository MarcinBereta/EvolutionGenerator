package mapElements;

import mapManager.MapSettings;

public class Gens {
    private int[] gens;
    private int genIndex;
    private MapEffects moveType;
    private MapSettings mapSettings;
    private MapEffects genGenereatorType;
    public Gens(MapSettings mapSettings){
        this.gens = new int[mapSettings.genSize];
        this.moveType = mapSettings.moveType;
        this.genGenereatorType = mapSettings.genType;
        this.mapSettings = mapSettings;
    }

    public void generateGens(Gens parent1, Gens parent2, int energy1, int energy2){
        int newgens[] = new int[gens.length];
        int site = (int)(Math.random()*100);
        int energySum = energy1 + energy2;
        int energy1Part = (int)(energy1/energySum);
        int energy2Part = (int)(energy2/energySum);
        if(energy1 > energy2){
            if(site > 50){
               for(int i = 0; i < (int) gens.length * energy1Part; i++){
                   newgens[i] = parent1.gens[i];
               }
               for(int i = (int) gens.length * energy1Part; i < gens.length; i++){
                   newgens[i] = parent2.gens[i];
               }
            }else{
                for(int i = 0; i < (int) gens.length * energy2Part; i++){
                    newgens[i] = parent2.gens[i];
                }
                for(int i = (int) gens.length * energy2Part; i < gens.length; i++){
                    newgens[i] = parent1.gens[i];
                }
            }
        }else{
            if(site > 50){
                for(int i = 0; i < (int) gens.length * energy2Part; i++){
                    newgens[i] = parent2.gens[i];
                }
                for(int i = (int) gens.length * energy2Part; i < gens.length; i++){
                    newgens[i] = parent1.gens[i];
                }
            }else{
                for(int i = 0; i < (int) gens.length * energy1Part; i++){
                    newgens[i] = parent1.gens[i];
                }
                for(int i = (int) gens.length * energy1Part; i < gens.length; i++){
                    newgens[i] = parent2.gens[i];
                }
            }
        }

        getRandomizeGens(newgens);

    }

    private void getRandomizeGens(int[] newgens){
        int gensToChange = this.mapSettings.minGens + (int)(Math.random()*((this.mapSettings.maxGens - this.mapSettings.minGens) + 1));
        int gensToChangeIndexes[] = new int[gensToChange];
        int genCounter = 0;
        while(genCounter < gensToChange){
            int index = (int)(Math.random()*newgens.length);
            boolean isUnique = true;
            for(int i = 0; i < genCounter; i++){
                if(gensToChangeIndexes[i] == index){
                    isUnique = false;
                }
            }
            if(isUnique){
                gensToChangeIndexes[genCounter] = index;
                genCounter++;
            }
        }
        if(genGenereatorType == MapEffects.FULLRANDOM){
            genCounter = 0;
            while (genCounter < gensToChange){
                int newGen = (int)(Math.random()*8);
                if(newGen != newgens[gensToChangeIndexes[genCounter]]){
                    newgens[gensToChangeIndexes[genCounter]] = newGen;
                    genCounter++;
                }
            }
        }else if(genGenereatorType == MapEffects.SMALLCORRECTION){
            genCounter = 0;
            while (genCounter < gensToChange){
                int ascOrDesc = (int)(Math.random()*2);
                if(ascOrDesc == 0){
                    if(newgens[gensToChangeIndexes[genCounter]] == 0){
                        newgens[gensToChangeIndexes[genCounter]] = 7;
                    }else{
                        newgens[gensToChangeIndexes[genCounter]]--;
                    }
                }else{
                    if(newgens[gensToChangeIndexes[genCounter]] == 7){
                        newgens[gensToChangeIndexes[genCounter]] = 0;
                    }else{
                        newgens[gensToChangeIndexes[genCounter]]++;
                    }
                }
                genCounter++;
            }
        }
        this.gens = newgens;
    }

    public int getGen(){
        int moveIndex = (int) (Math.random() * 100);
        if(moveType == MapEffects.BITOFMADDNESS){
            if (moveIndex > 80){
                genIndex = (genIndex + 2) % gens.length;
            }else{
                genIndex = (genIndex + 1) % gens.length;
            }
        }else if(moveType == MapEffects.FULLPREDESTINATION){
            genIndex = (genIndex + 1) % gens.length;
        }

        return gens[genIndex];
    }

    public void generateStartingGens(){
        int newGens[] = new int[gens.length];
        for(int i = 0; i < gens.length; i++){
            this.gens[i] = (int)(Math.random()*this.gens.length);
        }
    }

    public int[] getAllGens() {
        return gens;
    }

    @Override
    public String toString() {

        String result = "";
        for(int i = 0; i < this.gens.length; i++){
            result += gens[i] + " ";
        }
        return result;
    }
}
