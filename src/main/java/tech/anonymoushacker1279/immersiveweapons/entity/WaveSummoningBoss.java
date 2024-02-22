package tech.anonymoushacker1279.immersiveweapons.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;

public interface WaveSummoningBoss {

	boolean isDoneSpawningWaves();

	void setDoneSpawningWaves(boolean state);

	int getWavesSpawned();

	void setWavesSpawned(int waves);

	int getTotalWavesToSpawn();

	BossEvent getBossEvent();

	Component getWaveComponent();

	void playSummonSound();

	void playVulnerableSound();
}