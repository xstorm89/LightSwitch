<html>

<head>
    <title>CS580 iPhoto Web</title>
    <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>    
    <script src="/js/home-control.js"></script>
</head>

<body>
    
    <!-- User Info -->
    <hr>
    <div>
        <label>User Id: ${user.id}</label>
        <label>User Creation Time: ${user.creationTime}</label>
    </div>
    
    <!-- Photo Upload -->
    <hr>
    <div>    
        <form class="form-inline" action="/iphoto/${user.id}/photo" method="POST" enctype="multipart/form-data">                
                    <label>Photo File</label>
                    <input type="file" name="photoFile" id="photoFile"></input>
                    <input type="submit" value="Upload"></input>
        </form>
    </div>

    <!-- Followers & News Feed -->
    <hr>
    <div>
        <div>
            <label>Following:</label>
            <table>            
                <#list user.following as f>
                        <tr>
                          <td>${f}</td>
                          <td><button onclick="unfollowUser('${user.id}', '${f}')">Unfollow</button></td> 
                        </tr>                                                             
                </#list>
            </table>
            <label>Follow New User ID:</label><input id="newUserId" type="text"></input><button onclick="followUser('${user.id}')">Follow</button>
        </div>
        <div>
            <label>Followers:</label>
            <ul>
            <#list user.followers as follower>
                <li>
                    ${follower}
                </li>
            </#list>
            </ul>
        </div>
        <div>
            <label>News Feed:</label>
            <ul>
            <#list user.newsFeed as news>
                <li>
                    ${news}
                </li>
            </#list>
            </ul>
        </div>
    </div>
    
    <!-- Photos -->
    <hr>
    <div>
        <label>Photos:</label>    
        <ul>
            <#if photos??>
            <#list photos as photo>
              <li>
                <div>
                    <div>
                        ${photo}
                        <button id="deleteButton" onclick="deletePhoto('${user.id}', '${photo}')">Delete</button>
                    </div>
                    <div>
                        <#list filters as filter>
                            <input class="filter-checkbox" id="${photo}" type="checkbox" name="${filter}">${filter}</input>
                        </#list>
                        <div>
                            <button id="applyFilterButton" onclick="applyFilters('${user.id}', '${photo}')">Apply</button>
                        </div>
                    </div>
                    <div>
                        <a href="/iphoto/${user.id}/photo/${photo}" target="_blank">
                            <img src="/iphoto/${user.id}/photo/${photo}" width="240"/>
                        </a>
                    </div>
                </div>
              </li>
            </#list>
            </#if>
        </ul>
    </div>        

</body>

</html>