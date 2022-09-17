package com.gestankbratwurst.revenant.projectrevenant.util;

import com.gestankbratwurst.core.mmcore.util.common.UtilChunk;
import org.bukkit.Bukkit;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;

import java.awt.Color;

public class DynmapManager {

  private final DynmapAPI dynmapAPI;
  private final MarkerAPI markerAPI;
  private final MarkerSet chunkMarkerSet;

  private static final Color hotColor = new Color(233, 0, 0);
  private static final Color coldColor = new Color(5, 0, 233, 252);
  private static final float maxValue = 50000;

  public DynmapManager() {
    this.dynmapAPI = (DynmapAPI) Bukkit.getServer().getPluginManager().getPlugin("dynmap");
    if (dynmapAPI == null) {
      throw new RuntimeException("Could not create dynmapAPI!");
    }
    this.markerAPI = dynmapAPI.getMarkerAPI();
    this.chunkMarkerSet = markerAPI.createMarkerSet("chunk-noise", "Chunkheat", markerAPI.getMarkerIcons(), false);
  }


  public void setChunkMarker(long chunkKey, double heatValue) {
    int[] chunkPos = UtilChunk.getChunkCoords(chunkKey);
    double[] xArea = {chunkPos[0] * 16, chunkPos[0] * 16 + 16};
    double[] yArea = {chunkPos[1] * 16, chunkPos[1] * 16 + 16};
    String label = chunkKey + "";
    AreaMarker currentAreaMarker = chunkMarkerSet.findAreaMarker(label);
    Color fillColor = lerpRGB(Math.min((float) Math.min(heatValue, maxValue) / maxValue, 1.0f));
    if(currentAreaMarker != null){
      //ToDo holy macceroni das hier muss doch einfacher gehen
      currentAreaMarker.setFillStyle(0.4,  Integer.decode("0x" + Integer.toHexString(fillColor.getRGB()).substring(2)));
      currentAreaMarker.setDescription("Heat: " + heatValue);
    } else {
      AreaMarker newAreaMarker = chunkMarkerSet.createAreaMarker(label, "Chunkheat", true, Bukkit.getWorlds().get(0).getName(), xArea, yArea, false);
      if(newAreaMarker != null){
        newAreaMarker.setFillStyle(0.4, Integer.decode("0x" + Integer.toHexString(fillColor.getRGB()).substring(2)));
        newAreaMarker.setDescription("Heat: " + heatValue);
        newAreaMarker.setLineStyle(1, new Color(255, 255, 255).getRGB(), 1);
      }
    }
  }

  public void addMarker(int x, int z, String icon, String category, String description){
    Marker marker = chunkMarkerSet.createMarker(System.currentTimeMillis() + "", category, Bukkit.getWorlds().get(0).getName(), x, 0.0, z, markerAPI.getMarkerIcon(icon), true);
    marker.setLabel(category, true);
    marker.setDescription(description);
  }

  public static Color lerpRGB(float t) {
    float r = coldColor.getRed() + (hotColor.getRed() - coldColor.getRed()) * t;
    float g = coldColor.getGreen() + (hotColor.getGreen() - coldColor.getGreen()) * t;
    float b = coldColor.getBlue() + (hotColor.getBlue() - coldColor.getBlue()) * t;
    return new Color((int) (r + 0.5), (int) (g + 0.5), (int) (b + 0.5));
  }


}