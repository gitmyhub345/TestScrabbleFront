<%-- 
    Document   : scrabble
    Created on : Jan 25, 2018, 11:32:56 AM
    Author     : Rider1
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Scrabble Game</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
            <c:url value="/resources/css/scrabblecss.css" var="scrabblecss"/>
        <link rel="stylesheet" href="${scrabblecss}">
        <link rel="stlyesheet" href="<c:url value="/resources/css/base_plain.css" />">
        <script src="https://code.jquery.com/jquery-3.1.1.js" integrity="sha256-16cdPddA6VdVInumRGo6IbivbERE8p7CQR3HzTBuELA="   crossorigin="anonymous"></script>
	<script src="<c:url value="/resources/js/jquery-ui.js"/>"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <!--<script src="<c:url value="/resources/js/scrabblewebsocket.js"/>"></script>-->
        <script src="<c:url value="/resources/js/scrabble.js"/>"></script>
        <meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <meta name="_csrf" content="${_csrf.token}"/>
    </head>
    <body>
        <div class="divContainer">
            <div class="row" >
                <div class="col-md-10 header">
                        Dragon Cave's Scrabble Game: 
                </div>
                <div class="col-md-2 topmenu">
                    <img class="profile" id="profile" src="<c:url value="/resources/images/profile/TLuser.png" />" width="30px" height="30px">
                </div>
            </div>
            <div id="menusection">
                    <ul id="scrabblemenu">
                        <li><div>menu</div>
                            <ul>
                                <li><div id="newjoingame">New/Join Game</div></li>
                                <li><div id="startgame" class="ui-state-disabled">Start Game</div></li>
                                <li><div id="endgame" class="ui-state-disabled">End Game</div></li>
                            </ul>
                        </li>
                    </ul>
            </div>
            <div id="screenswitcher"><input id="showBoard" type="button" value="board"><input id="showStats" type="button" value="players words"></div>
            <hr>
            <div class="row">
                <div id="boardcontainer">
                    <div class="staticsection">
                        <div><span id="status"></span></div>
                        <div><span class="label">message:</span><span class="stats" id="message"></span></div>
                        <div><span class="label">My Name:</span><span class="stats" id="playerName">MyScreenName</span></div>
                        <div><span class="label">My Score:</span><span class="stats" id="score">0</span></div>
                        <div><span class="label">Game Name:</span><span class="stats" id="gameName">Roaring</span></div>
                        <div><span class="label">Current player:</span><span class="stats" id="currentPlayer"></span></div>
                        <div><span class="label">Play score:</span><span class="stats" id="playScore">0</span></div>
                        <div><span class="label">Mode:</span><span class="stats" id="mode">Game mode</span></div>
                        <div><span class="label">Words played:</span><span class="stats" id="wordsPlayed">blah<br>blah<br>blah</span></div>
                        <div><span class="label">Stats:</span><span class="stats" id="playersStats"></span></div>
                    </div>
                    <div id="board">
                        <%! int maxRow; int maxCol; String[][] tileHolder = {{"tws","sls","sls","dls","sls","sls","sls","tws","sls","sls","sls","dls","sls","sls","tws"},
                                                {"sls","dws","sls","sls","sls","tls","sls","sls","sls","tls","sls","sls","sls","dws","sls"},
                                                {"sls","sls","dws","sls","sls","sls","dls","sls","dls","sls","sls","sls","dws","sls","sls"},
                                                {"dls","sls","sls","dws","sls","sls","sls","dls","sls","sls","sls","dws","sls","sls","dls"},
                                                {"sls","sls","sls","sls","dws","sls","sls","sls","sls","sls","dws","sls","sls","sls","sls"},
                                                {"sls","tls","sls","sls","sls","tls","sls","sls","sls","tls","sls","sls","sls","tls","sls"},
                                                {"sls","sls","dls","sls","sls","sls","dls","sls","dls","sls","sls","sls","dls","sls","sls"},
                                                {"tws","sls","sls","dls","sls","sls","sls","dws","sls","sls","sls","dls","sls","sls","tws"},
                                                {"sls","sls","dls","sls","sls","sls","dls","sls","dls","sls","sls","sls","dls","sls","sls"},
                                                {"sls","tls","sls","sls","sls","tls","sls","sls","sls","tls","sls","sls","sls","tls","sls"},
                                                {"sls","sls","sls","sls","dws","sls","sls","sls","sls","sls","dws","sls","sls","sls","sls"},
                                                {"dls","sls","sls","dws","sls","sls","sls","dls","sls","sls","sls","dws","sls","sls","dls"},
                                                {"sls","sls","dws","sls","sls","sls","dls","sls","dls","sls","sls","sls","dws","sls","sls"},
                                                {"sls","dws","sls","sls","sls","tls","sls","sls","sls","tls","sls","sls","sls","dws","sls"},
                                                {"tws","sls","sls","dls","sls","sls","sls","tws","sls","sls","sls","dls","sls","sls","tws"}}; %> 
                        <% for (maxRow = 0; maxRow < 15; maxRow++){%>
                            <% for (maxCol = 0; maxCol < 15; maxCol++){%>
                                <div class="boardTileHolder <%=tileHolder[maxRow][maxCol] %>" id="<%=maxRow%>-<%=maxCol %>"></div>
                            <%}%>
                            <br>
                        <%}%>

                    </div>
                    <div id="hand">
                        <% for (int row=0; row < 7; row++) {%>
                            <div class="handTileHolder" id="<%="t"+row %>"></div>
                        <% } %>

                    </div>
                <!--<div class="row">-->
                    <div class="actions">
                        <input type="button" id="submittiles" name="submittiles" value="submit tiles" disabled="true">
                        <input type="button" id="returntiles" name="returntiles" value="return tiles" disabled="true">
                        <!--<input type="button" id="startgame" name="startgame" value="start game" disabled="true">-->

                    </div>
                <!--</div>-->
                </div>
                <!-- statistics screen-->
                <div id="statsScreen">
                        <div id="statsForm">
                                <div id="p1" class="playerStats">
                                    <div id="p1name"></div><br><div id="p1words"></div>
                                </div>
                                <div id="p2" class="playerStats">
                                    <div id="p2name"></div><br><div id="p2words"></div>
                                </div>
                                <div id="p3" class="playerStats">
                                    <div id="p3name"></div><br><div id="p3words"></div>
                                </div>
                                <div id="p4" class="playerStats">
                                    <div id="p4name"></div><br><div id="p4words"></div>
                                </div>

                        </div>
                </div>
            <!-- end statistics screen-->
            </div>
        </div>
	<div id="newgame">
            <div id="newgameform">
                <label id="newGameStatus"></label><br>
                <label>game name: *</label><input type="text" id="newGameName" name="gameName" size=30 maxlength=30><br>
                <label>your name: *</label><input type="text" id="newPlayerName" name="playerName" size=30 maxlength=30><br>
                <input type="button" id="btnNewGame" name="btnNewGame" value="create" >
                <input type="button" id="btnJoinGame" name="btnJoinGame" value="join game" >
                <input type="button" id="btnCancel" name="btnCancel" value="cancel" >
            </div>
	</div>
        <div id="challengeScreen">
            <div id="challengeForm">
                <div id="playedWords">
                    <label style="text-align: center;">Do you wish to challenge and of the following words formed? </label><br>
                    <label style="text-align: center;" id="words">sample word</label><br><br>
                    <input type="button" id="btnChallengeNo" value="No">
                    <input type="button" id="btnChallengeYes" value="Yes">
                </div>
            </div>
        </div>
        <div id="challengeConfirmationScreen">
            <div id="challengeConfirmationForm">
                <input type="button" id ="btnChallengeConfirmationNo" value="Not valid">
                <input type="button" id="btnChallengeConfirmationYes" value="Valid">
            </div>
        </div>
    </body>
</html>
