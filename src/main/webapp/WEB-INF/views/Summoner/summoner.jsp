<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">	
</script>
</head>
<body>
	<h1>summoner.jsp</h1>
	<form action="summoner" method="get">
		<input type="text" name="name" style="right: 50px;"><input type="submit" value="검색">
	</form>
	<hr>
		<table>
			<tr>
				<td rowspan="2">
					<img src="http://ddragon.leagueoflegends.com/cdn/12.19.1/img/profileicon/${dto.profileIconId }.png" width="75px" height="75px">
				</td>
				
			</tr>
			<tr>				
				<td>
					<h2>${dto.name }의 정보</h2>
				</td> 
			</tr>
			
			
		</table>						

	<table border="1">
		<tr>
			<th>레벨</th><th>아이디</th><th colspan="2">랭크</th>
		</tr>
		<tr>
			<td>${dto.summonerLevel }</td><td>${dto.name }</td><td>${rank.tier } <c:if test="${rank.leaguePoints != -1}">${rank.rank } ${rank.leaguePoints}LP</c:if></td><c:if test="${rank.wins != 0 && rank.losses != 0}"><td>${rank.wins }승 / ${rank.losses }패</td></c:if>
		</tr>
	</table>
	<hr>
	<h2>최근 플레이(가장 최근 플레이)</h2>
	<table border="1">
		<tr>
			<th></th><th>게임모드</th><th>챔피언</th><th>승/패</th><th>K/D/A</th><th>챔피언 대상 피해량</th><th>획득 골드</th>
		</tr>
		<c:forEach var="mdto" items="${matchDTOList }">
		<tr>
			<td>${mdto.end }</td><td>${mdto.gameMode }<br>${mdto.gameDuration }</td><td class="champ"><img src="http://ddragon.leagueoflegends.com/cdn/12.20.1/img/champion/${mdto.championImg }.png" width="40px" height="40px">${mdto.championName } cs : ${mdto.totalMinionsKilled+mdto.neutralMinionsKilled }<br>			
				<img src="http://ddragon.leagueoflegends.com/cdn/12.20.1/img/item/${mdto.item0}.png" width="25px" height="25px" onerror="this.style.display='none'" alt="">
				<img src="http://ddragon.leagueoflegends.com/cdn/12.20.1/img/item/${mdto.item1}.png" width="25px" height="25px" onerror="this.style.display='none'" alt="">
				<img src="http://ddragon.leagueoflegends.com/cdn/12.20.1/img/item/${mdto.item2}.png" width="25px" height="25px" onerror="this.style.display='none'" alt="">
				<img src="http://ddragon.leagueoflegends.com/cdn/12.20.1/img/item/${mdto.item3}.png" width="25px" height="25px" onerror="this.style.display='none'" alt="">
				<img src="http://ddragon.leagueoflegends.com/cdn/12.20.1/img/item/${mdto.item4}.png" width="25px" height="25px" onerror="this.style.display='none'" alt="" >
				<img src="http://ddragon.leagueoflegends.com/cdn/12.20.1/img/item/${mdto.item5}.png" width="25px" height="25px" onerror="this.style.display='none'" alt="">
				<img src="http://ddragon.leagueoflegends.com/cdn/12.20.1/img/item/${mdto.item6}.png" width="25px" height="25px" onerror="this.style.display='none'" alt="">
			</td><td><c:if test="${mdto.win ==true}">승리</c:if><c:if test="${mdto.win !=true}">패배</c:if></td><td>${mdto.kills }/${mdto.deaths }/${mdto.assists }</td><td>${mdto.totalDamageDealtToChampions }</td><td>${mdto.goldEarned }골드</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>