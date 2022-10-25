package com.root.ddoopgg.user.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.catalina.util.URLEncoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.root.ddoopgg.Config;
import com.root.ddoopgg.user.dto.MatchDTO;
import com.root.ddoopgg.user.dto.UserDTO;
import com.root.ddoopgg.user.dto.UserRankDTO;




@Service
public class UserServiceImpl implements UserService {

	@Override
	public void getSummoner(String name,Model model) {
		ObjectMapper objectMapper = new ObjectMapper();
		UserDTO dto = null;
		
		String url = "https://kr.api.riotgames.com/";
		String api = Config.APIKEY;
		String SummonerName = name.replaceAll(" ", "%20");
		String requestURL = url+"lol/summoner/v4/summoners/by-name/"+name+"?api_key="+api;				
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(requestURL);
			HttpResponse response = client.execute(getRequest);
			
			if(response.getStatusLine().getStatusCode()==200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				//System.out.println(body);
				dto = objectMapper.readValue(body, UserDTO.class);
				System.out.println(dto.getName());
				System.out.println(dto.getSummonerLevel());
				System.out.println(dto.getPuuid());
				System.out.println(dto.getId());
				model.addAttribute("dto",dto);
				String rankURL = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/";
				String requestRankURL = rankURL+dto.getId()+"?api_key="+api;
				getRequest = new HttpGet(requestRankURL);
				response = client.execute(getRequest);
				if(response.getStatusLine().getStatusCode()==200) {
					String rankBody = handler.handleResponse(response);
					rankBody = rankBody.replace("[", "");
					rankBody = rankBody.replace("]", "");
					rankBody = rankBody.replace("},", "}#");
					//System.out.println("rankbody : "+rankBody);
					String[] rank = rankBody.split("#");
					ArrayList<String> rankList = new ArrayList<String>();
					UserRankDTO rdto = new UserRankDTO();
					for(int i=0;i<rank.length;i++) {
						if(rank[i].equals("")) {
							rdto.setTier("UnRanked");
							rdto.setLeaguePoints(-1);
							model.addAttribute("rank",rdto);
							break;
						}else{
							rankList.add(rank[i]);
							//System.out.println("rankList"+i+" : "+rankList.get(i));
							//System.out.println(rankList.isEmpty());
							if(!rankList.isEmpty()) {
								Map<String,String> map = objectMapper.readValue(rank[i], Map.class);
								//System.out.println(i+"번째 : "+map);
								if(map.get("queueType").equals("RANKED_SOLO_5x5")) {
									
									//System.out.println(map.get("tier"));
									//System.out.println(map.get("rank"));
									//System.out.println(String.valueOf(map.get("leaguePoints")));
									//System.out.println(String.valueOf(map.get("wins")));
									//System.out.println(String.valueOf(map.get("losses")));
									rdto = objectMapper.convertValue(map, UserRankDTO.class);
									model.addAttribute("rank",rdto);
									break;
								}
							}
						}
					}
				}
				
				
				String matchHistoryURL = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/";
				String requestMatchHitoryURL = matchHistoryURL+dto.getPuuid()+"/ids?start=0&count=20&api_key="+api;
				getRequest = new HttpGet(requestMatchHitoryURL);
				response = client.execute(getRequest);
				if(response.getStatusLine().getStatusCode()==200) {
					String matchHistoryBody = handler.handleResponse(response);					
					//System.out.println("받아온 정보 : \n"+matchHistoryBody);
					matchHistoryBody = matchHistoryBody.replace("[", "");
					matchHistoryBody = matchHistoryBody.replace("]", "");
					matchHistoryBody = matchHistoryBody.replace("\"", "");					
					//System.out.println("받아온 정보 : \n"+matchHistoryBody);
					String[] mList = matchHistoryBody.split(",");
					String rankMatchURL = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/";
					String requestRankMatchURL = rankMatchURL+dto.getPuuid()+"/ids?type=ranked&start=0&count=20&api_key="+api;
					getRequest = new HttpGet(requestRankMatchURL);
					response = client.execute(getRequest);
					String rankMatchBody = handler.handleResponse(response);
					System.out.println(rankMatchBody);
					rankMatchBody = rankMatchBody.replace("[", "");
					rankMatchBody = rankMatchBody.replace("]", "");
					rankMatchBody = rankMatchBody.replace("\"", "");
					String[] rMList = rankMatchBody.split(",");		
					
					ArrayList<Object> matchList = new ArrayList<Object>();
					ArrayList<Object> rankMatchList = new ArrayList<Object>();
					ArrayList<MatchDTO> matchDTOList = new ArrayList<MatchDTO>();
					for(int i =0; i<mList.length;i++) { 						
						matchList.add(mList[i]);						
						//System.out.println("matchList["+i+"] : "+matchList.get(i)); 
						String matchURL = "https://asia.api.riotgames.com/lol/match/v5/matches/";
						String requestMatchURL = matchURL+matchList.get(i)+"?api_key="+api;										
						getRequest = new HttpGet(requestMatchURL);
						response = client.execute(getRequest);
						if(response.getStatusLine().getStatusCode()==200) {
							MatchDTO mdto =null;
							String matchBody = handler.handleResponse(response);
							//System.out.println("받아온 정보 : "+matchBody);
							Map<String,Map> map = objectMapper.readValue(matchBody, Map.class);
							//System.out.println(map);
							//System.out.println(map.size());
							//System.out.println(map.keySet());
							//System.out.println(map.get("metadata"));
							//System.out.println(map.get("metadata").get("participants"));
							String puuids = map.get("metadata").get("participants").toString().replace("[", "");
							puuids = puuids.replace("]", "");
							puuids = puuids.replace(" ", "");
							String[] id =puuids.split(",");
							//System.out.println(id.length);
							for(int j=0;j<id.length;j++) {
								ArrayList<Map<String,Object>> matchTeamList = (ArrayList<Map<String, Object>>) map.get("info").get("participants");
								//System.out.println(matchTeamList.get(j).get("teamId"));								
								if(id[j].equals(dto.getPuuid())) {
									//System.out.println("puuid+["+i+"] : "+id[j]);
									//System.out.println(i);
									//System.out.println(map.get("info").get("participants"));
									ArrayList<Map> list = (ArrayList<Map>) map.get("info").get("participants");																		
									//System.out.println(map.get("info").get("gameEndTimestamp"));									
									//System.out.println("list ["+i+"] : "+list.get(i));
									//System.out.println(list.get(i).get("championName"));																
									mdto = objectMapper.convertValue(list.get(j), MatchDTO.class);
									mdto.setMatchID((String) matchList.get(i));									
									mdto.setChampionImg(mdto.getChampionName());
									//System.out.println(mdto.getItem0());
									long time = (long) map.get("info").get("gameEndTimestamp")/1000; //게임끝난 시각
									long now = System.currentTimeMillis()/1000; //현재 시각
									long end = (now - time);
									//System.out.println("지금"+now);
									//System.out.println(end);
									mdto.setGameEndTimestamp(Long.toString(time));
									mdto.setEnd(Long.toString(end));
									long start = (long) map.get("info").get("gameStartTimestamp")/1000;
									long durate = time-start;
									SimpleDateFormat format = new SimpleDateFormat("mm분 ss초");
									Date date = new Date();
									date.setTime(durate*1000);									
									//System.out.println(format.format(date));
									mdto.setGameDuration(format.format(date));
									//System.out.println("시간 : "+mdto.getGameEndTimestamp());
									Object data = null;
									String champUrl = "https://ddragon.leagueoflegends.com/cdn/12.20.1/data/ko_KR/champion.json";								
									HttpGet getChampRequest = new HttpGet(champUrl);
									//System.out.println(getChampRequest);
									HttpResponse champResponse = client.execute(getChampRequest);
									//System.out.println(champResponse);
									BufferedReader br = new BufferedReader(new InputStreamReader(champResponse.getEntity().getContent(),"UTF-8"));																
									String encode = br.readLine();
									br.close();
									JSONParser jparser = new JSONParser();
									data = jparser.parse(encode);
									String sdata =  data.toString();
									//System.out.println(sdata);
																											
									Map<Object,Map> champMap = objectMapper.readValue(sdata, Map.class);
									//System.out.println(champMap.get("data")); 
									Map<String,Map> champNameMap =	objectMapper.convertValue(champMap.get("data"), Map.class);
									//System.out.println(champNameMap.keySet());
									//System.out.println(champNameMap.get(mdto.getChampionName()).get("name")+" "
											//+champNameMap.get(mdto.getChampionName()).get("key"));
									//System.out.println(mdto.getChampionId());
									//System.out.println(mdto.getChampionName());
									ArrayList<String> champKey =new ArrayList<String>(champNameMap.keySet());
									//System.out.println(champNameMap.keySet());
									//System.out.println(champNameMap.get(mdto.getChampionName()));
									//System.out.println(champNameMap.containsKey(mdto.getChampionName()));
									for(int k=0;k<champKey.size();k++) {
										if(mdto.getChampionName().equalsIgnoreCase(champKey.get(k))) {						
											//System.out.println(champKey.get(k));
											mdto.setChampionName(champKey.get(k));
											break;
										}
									}																			
									
									if(mdto.getChampionId()==Integer.parseInt((String)champNameMap.get(mdto.getChampionName()).get("key"))) {
										mdto.setChampionName((String)champNameMap.get(mdto.getChampionName()).get("name")); 
									}									
									mdto.setGameMode((String)map.get("info").get("gameMode"));	
									//System.out.println(mdto.getGameMode());
									if(mdto.getGameMode().equals("CLASSIC")) {
										mdto.setGameMode("일반");
									}else if(mdto.getGameMode().equals("ARAM")) {
										mdto.setGameMode("무작위 총력전");										
									}else if(mdto.getGameMode().equals("URF")) {
										mdto.setGameMode("U.R.F");										
									}else if(mdto.getGameMode().equals("ULTBOOK")) {
										mdto.setGameMode("궁극기 주문서");																				
									}
									for(int m=0; m<rMList.length;m++) {
										rankMatchList.add(rMList[m]);
										if(rankMatchList.get(m).equals(mdto.getMatchID())) {
											mdto.setGameMode("랭크 게임");
										}																					
									}									
									matchDTOList.add(mdto);
									//System.out.println(mdto.getChampionName());
									//System.out.println(mdto.getKills()+"/"+mdto.getDeaths()+"/"+mdto.getAssists());									
								}
							}							
							model.addAttribute(matchDTOList);
						}					 
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}				
	}	
}
