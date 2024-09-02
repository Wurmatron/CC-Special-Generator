package io.wurmatron.ccspecial.api;

public class PlanentEntry {

    public String outerBlock;
    public int outerMeta;

    public String fillerBlock;
    public int fillerMeta;

    public String fillerRareBlock;
    public int fillerRareMeta;

    public String coreBlock;
    public int coreMeta;

    public int rarityEntry; // Lower == More Rare

    public PlanentEntry(String outerBlock, int outerMeta, String fillerBlock, int fillerMeta, String fillerRareBlock, int fillerRareMeta, String coreBlock, int coreMeta, int rarityEntry) {
        this.outerBlock = outerBlock;
        this.outerMeta = outerMeta;
        this.fillerBlock = fillerBlock;
        this.fillerMeta = fillerMeta;
        this.fillerRareBlock = fillerRareBlock;
        this.fillerRareMeta = fillerRareMeta;
        this.coreBlock = coreBlock;
        this.coreMeta = coreMeta;
        this.rarityEntry = rarityEntry;
    }
}
