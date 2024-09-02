package io.wurmatron.ccspecial.api;

public class SkyblockEntry {

    public String block;
    public int meta;
    public int rarityEntry; // Lower == More Rare

    public SkyblockEntry(String block, int meta, int rarityEntry) {
        this.block = block;
        this.meta = meta;
        this.rarityEntry = rarityEntry;
    }
}
