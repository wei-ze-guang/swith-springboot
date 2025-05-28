##部署  
1. 写好DockerFile文件之后  
2. 构建容器  
  - ```docker build -t my-java-app .```  这个my-java-app是容器名字 这个.  他会自动找dockerFile文件  
3. 运行容器 ```docker run --rm my-java-app```
