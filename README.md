###### Start Server
```
cd src/main/resources
java ../java/NFileServer.java
```

###### Start Client
```
cd src/main/java
java NFileClient.java
```

###### Sample run
```
long@long-pc:~/IdeaProjects/git/NFile/src/main/java$ java NFileClient.java 
Message:                                                                                          
Hi Client                                                                                         
hello                                                                                             
Message:                                                                                          
Unknown Command                                                                                   
index                                                                                             
Message:                                                                                          
b.txt                                                                                             
c.txt                                                                                             
a.txt                                                                                            
                                                                                                  
get                                                                                               
Message:                                                                                          
error                                                                                             
get a.txt
Message:
a
```
