package com.ssacation.jpamodel.controller;
import java.util.*;
import java.io.*;
public class Main {
    static class Node{
        int idx, cost;
        public Node(int idx, int cost){
            this.idx = idx;
            this.cost = cost;
        }
    }
    static int N;

    static ArrayList<Node>[] arr;
    static boolean[] visit;
    static int max = 0;
    static int maxIdx = 0;
    public static void main(String[] args) throws IOException{
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(bf.readLine());
        StringTokenizer st;
        arr= new ArrayList[N+1];
        visit = new boolean[N+1];
        for(int i=0; i<=N; i++) arr[i] = new ArrayList<>();
        for(int i=0; i<N-1; i++) {
            st = new StringTokenizer(bf.readLine());
            int num1 = Integer.parseInt(st.nextToken());
            int num2 = Integer.parseInt(st.nextToken());
            int num3 = Integer.parseInt(st.nextToken());
            arr[num1].add(new Node(num2, num3));
            arr[num2].add(new Node(num1, num3));
        }
        visit = new boolean[N+1];
        visit[1] = true;
        dfs(1,0);

        visit = new boolean[N+1];
        visit[maxIdx] = true;
        dfs(maxIdx,0 );
        System.out.println(max);

    }
    static void dfs(int idx, int cost) {
        if(max < cost) {
            max = cost;
            maxIdx = idx;
        }

        for(Node a : arr[idx]){
            if(!visit[a.idx]){
                visit[a.idx] = true;
                dfs(a.idx, cost+a.cost);
            }
        }
    }
}
