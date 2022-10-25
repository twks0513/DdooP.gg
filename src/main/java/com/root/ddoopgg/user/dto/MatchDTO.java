package com.root.ddoopgg.user.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchDTO {
	String summonerName,gameMode,championName,championImg,gameEndTimestamp,end,gameDuration,matchID;
	boolean win;
	int kills,deaths,assists,goldEarned,totalDamageDealtToChampions,championId,item0,item1,item2,item3,item4,item5,item6,neutralMinionsKilled,totalMinionsKilled,teamId;
	public String getSummonerName() {
		return summonerName;
	}
	public void setSummonerName(String summonerName) {
		this.summonerName = summonerName;
	}
	public String getGameMode() {
		return gameMode;
	}
	public void setGameMode(String gameMode) {
		this.gameMode = gameMode;
	}
	public String getChampionName() {
		return championName;
	}
	public void setChampionName(String championName) {
		this.championName = championName;
	}
	public boolean isWin() {
		return win;
	}
	public void setWin(boolean win) {
		this.win = win;
	}
	public int getKills() {
		return kills;
	}
	public void setKills(int kills) {
		this.kills = kills;
	}
	public int getDeaths() {
		return deaths;
	}
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	public int getAssists() {
		return assists;
	}
	public void setAssists(int assists) {
		this.assists = assists;
	}
	public int getGoldEarned() {
		return goldEarned;
	}
	public void setGoldEarned(int goldEarned) {
		this.goldEarned = goldEarned;
	}
	public int getTotalDamageDealtToChampions() {
		return totalDamageDealtToChampions;
	}
	public void setTotalDamageDealtToChampions(int totalDamageDealtToChampions) {
		this.totalDamageDealtToChampions = totalDamageDealtToChampions;
	}
	public int getChampionId() {
		return championId;
	}
	public void setChampionId(int championId) {
		this.championId = championId;
	}
	public String getChampionImg() {
		return championImg;
	}
	public void setChampionImg(String championImg) {
		this.championImg = championImg;
	}
	public String getGameEndTimestamp() {
		return gameEndTimestamp;
	}
	public void setGameEndTimestamp(String gameEndTimestamp) {
		long time = Long.parseLong(gameEndTimestamp);
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Date date = new Date();
		date.setTime(time);
		
		this.gameEndTimestamp = sdf.format(date);
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		long time = Long.parseLong(end);
		double result = time/60;
		//System.out.println("time : "+time);
		//System.out.println("result : "+result);
		if(result >= 1440 ) {
			this.end = Math.round(result/60/24)+"일 전";
		}else if(result < 1440 && result >= 60) {		
			this.end = Math.round(result/60)+"시간 전";	
		}else if(result < 60) {
			this.end = Math.round(result)+"분 전";						
		}
	}
	public int getItem0() {
		return item0;
	}
	public void setItem0(int item0) {
		this.item0 = item0;
	}
	public int getItem1() {
		return item1;
	}
	public void setItem1(int item1) {
		this.item1 = item1;
	}
	public int getItem2() {
		return item2;
	}
	public void setItem2(int item2) {
		this.item2 = item2;
	}
	public int getItem3() {
		return item3;
	}
	public void setItem3(int item3) {
		this.item3 = item3;
	}
	public int getItem4() {
		return item4;
	}
	public void setItem4(int item4) {
		this.item4 = item4;
	}
	public int getItem5() {
		return item5;
	}
	public void setItem5(int item5) {
		this.item5 = item5;
	}
	public int getItem6() {
		return item6;
	}
	public void setItem6(int item6) {
		this.item6 = item6;
	}
	public String getGameDuration() {
		return gameDuration;
	}
	public void setGameDuration(String gameDuration) {
		this.gameDuration = gameDuration;
	}
	public int getNeutralMinionsKilled() {
		return neutralMinionsKilled;
	}
	public void setNeutralMinionsKilled(int neutralMinionsKilled) {
		this.neutralMinionsKilled = neutralMinionsKilled;
	}
	public int getTotalMinionsKilled() {
		return totalMinionsKilled;
	}
	public void setTotalMinionsKilled(int totalMinionsKilled) {
		this.totalMinionsKilled = totalMinionsKilled;
	}
	public String getMatchID() {
		return matchID;
	}
	public void setMatchID(String matchID) {
		this.matchID = matchID;
	}
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	
	
}
