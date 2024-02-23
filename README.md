# AWS - github&jenkins 연동
### 서버 구성 drawed by EG
![image](https://github.com/yuri-notfragile/jenkins_withEC2/assets/131278132/178d8093-a9d2-4cc3-91cd-c433a5c097c6)


### 보안 그룹 설정 - Jenkins 사용을 위한 인바운드 8080 포트 열기
    - 인바운드 규칙 편집
        - Custom TCP Rule
        - Port range : 8080
        - Source : 0.0.0.0/0(모든 IP 허용)


### SWP file로 메모리 할당

**스왑 파일은 물리적 RAM보다 훨씬 느리기 때문에 스왑이 발생하면 성능 저하 가능**

**스왑 파일은 메모리의 일부를 대체할 뿐, 확장적인 메모리가 아님!**

- AWS Free tier EC2 스토리지 용량은 실제로 30GB
- jenkins build 시 부족
- https://repost.aws/ko/knowledge-center/ec2-memory-swap-file



### jenkins webhook 설정
1. AWS ip:8080으로 접속
2. Jenkins 관리 > 플러그인 관리 > 사용 가능 > GitHub 플러그인
3. Settings - Webhooks
4. Payload URL : <Jenkins URL>/github-webhook/
5. Content type : "application/json"
6. 구성 - Build Triggers
7. General - GitHub Repo 주소 입력


#### 명령어 & Pipeline Script
```
sudo apt-get update

sudo apt install git

git config --global user.name ~
git config --global user.mail ~
git clone https://github.com/yuri-notfragile/~

sudo apt install -y docker.io

docker run --name myjenkins --privileged -p 8080:8080 jenkins/jenkins:lts-jdk17

# AWS ip:8080으로 접속

```

```
pipeline {
    agent any

    stages {
    stage('review1') {
        steps {
            git branch: 'main', 
            credentialsId: '', 
            url: 'https://github.com/~.git'
            }
        }
    stage('review2') {
        steps {
            sh '''
                chmod +x gradlew
            '''
            }
        }
	stage('review3') {
        steps {
            sh '''
                echo 'build start'
                ./gradlew clean bootJar
            '''
            }   
        }
    }
}
```
