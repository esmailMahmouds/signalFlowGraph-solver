<template>
      

      <div class="buttonsbar">
        <div class="machine">
          <Button @click="addMachine" style="font-weight: 400;">N+</Button>
          <p>Add Node</p>
        </div>
        <div style="display: flex; flex-direction: row;">
          <p style="font-size: 25px; font-weight: 400;">No. of nodes: </p>
          <InputNumber style="width: 150px; height: 40px; position: relative; margin-top: 18px; margin-left: 5px;" v-model="numnodes" inputId="minmax" :min="2" :max="200"  />
        </div>
        
        <div style="display: flex; flex-direction: row;">
          <Button @click="initialize" style="width: 150px; margin-left: -50px; font-weight: 400;">Save Values</Button>
          <Button @click="showmodal=true" style="width: 120px; margin-left: 200px; font-weight:400; text-align: center; vertical-align:middle;" >Connect</Button>
        </div>
        <Button @click="clear" class="pi pi-trash" style="font-weight: 400; align-content:center;"></Button>
  
        <div class="sim-controls">
          <Button @click="simulate" style="margin-right: 10px; font-weight: 400;" class="pi pi-play"></Button>
          <!-- <button style="margin-right: 10px;" class="pi pi-stop"></button> -->
        </div>
      </div>
      <div class="modal"  v-if="showmodal">
        <div style="display: flex; flex-direction: row; margin-top: 25px; margin-left: 35px;">
          <p style="font-size: 25px; ">Enter branch gain: </p>
          <InputNumber style=" font-size:larger; width: 150px; height: 40px; position: relative; margin-top: 18px; margin-left: 5px;" v-model="gainamount" inputId="minmax" :min="-200" :max="200" /> 
          <button @click="connect" style="margin-left: 75px;">Ok</button>
        </div>
        </div>
        <!-- <div class="overlay" v-if="showmodal"></div>
        <div class="modal" style="width: 1900px; height: 1100px; background-color: cyan;">
          <div style="display: flex; flex-direction: row; margin-top: 25px; margin-left: 35px;">
            <p style="font-size: 25px; ">Enter branch gain: </p>
            <InputNumber style=" font-size:larger; width: 150px; height: 40px; position: relative; margin-top: 18px; margin-left: 5px;" v-model="gainamount" inputId="minmax" :min="-200" :max="200" /> 
            <button @click="connect" style="margin-left: 75px;">Ok</button>
          </div> -->
          <!-- </div>  -->
          
          
          
          
          <div style="height: 800px; width: 100%; border: 2px solid black; border-radius: 10px;"> 
            <v-network-graph
            :nodes="nodes"
            :edges="edges" 
            :configs="configures"
            v-model:selectedEdges="selectedEdges"
            v-model:selectedNodes="selectedNodes"
            edge-label="edges"
            >
            <template #edge-label="{ edge, ...slotProps }">
              <v-edge-label :text="edge.label" align="center" vertical-align="above" v-bind="slotProps" >
              </v-edge-label>
              
            </template>
          </v-network-graph>
      </div>
  </template>
  
  <script>
  import InputNumber from 'primevue/inputnumber';
  import axios from 'axios'
  import {VNetworkGraph } from "v-network-graph"
  import {defineConfigs } from "v-network-graph"
  import Button from 'primevue/button'; 
  export default {
    components:{
      Button,
      InputNumber,
      VNetworkGraph,
      
    },
  
  data(){
  
    return{
  
      selectedNodes: [],
      selectedEdges: [],
      nodes:[],
      selected:[],
      adjacency:[],
      linelist:[],
      edges:[],
      numnodes:2,
      gains:1,
      gainamount:1,
      showmodal:false,
     configures :defineConfigs({
    node: {
    directionAutoAdjustment: true,
    selectable: 2,
    normal:{
      type: "circle",
      radius:20,
    },
    label:{
      visible:true,
      position:'center',
      fontSize: 20,
    },
    }, // up to 2 nodes
    
    
    
    edge: {
    gap:70,
    marker: {
      target: { type: "arrow" },
    },
    type:'curve',
    selectable: true,
    normal: {
      width: 3,
      radius:100,
    },
    selfLoop: {
      radius: 14,
      offset: 16,
      angle: 0,
      isClockwise: true,
    },
    label:{
      visible:true,
      position:'center',
      fontSize: 50,
    },
  },
}),
  
    };
  },
  
  mounted() {    
      //  const screenWidth = 2940;
      //  const screenHeight = 850;   
       
     }, 
     methods:{
  
    
    clear()
      {
        //write ere the api responsible for clearing everything in the backend 
  
        axios.delete("http://localhost:8081/clear");
  
  
        this.adjacency=[];
        console.table(this.adjacency)
        for(let i =0;i<this.queues.length;i++)
         {
            this.queues[i].destroy();
         }
        this.queues=[];
  
        for(let i =0;i<this.machines.length;i++)
         {
            this.machines[i].destroy();
         }
        this.machines=[];
        for(let i =0;i<this.linelist.length;i++)
         {
            this.linelist[i].destroy();
         }
         this.linelist=[];
        this.selected=[];
        this.tr.nodes([]);
        
      },
      handleNodeClick(){
        console.log("Node clicked");
      },
      

  
      
  
      simulate(){
        console.table(this.adjacency);
        const body={architecture:this.adjacency}
        axios.post(`http://localhost:8081/construct/${this.number}`,body);
        this.startFetchData1();
        
  
  
  //
      },
  
      connect(){
        if(this.selectedNodes.length==1){
          const customedge={
          source: this.selectedNodes[0],
          target: this.selectedNodes[0],
          label: this.gainamount.toString(),
        }
            this.edges.push(customedge);
            this.adjacency.push([this.selectedNodes[0],this.selectedNodes[0],this.gainamount]);
            this.gainamount=1;
            this.showmodal=false;
            console.table(this.adjacency)
            
          }
          
          else if(this.selectedNodes.length==2){
            const customedge={
              source: this.selectedNodes[0],
              target: this.selectedNodes[1],
              label: this.gainamount.toString(),
            }
            this.edges.push(customedge);
            console.log("edege"+this.edges[0].label);
            this.adjacency.push([this.selectedNodes[0],this.selectedNodes[1] ,this.gainamount]);
          this.gainamount=1;
          this.showmodal=false;
          console.table(this.adjacency)
      }
      else{
        alert("Please select one or two nodes to connect")
        this.showmodal=false;
      }
      
        
      },
    
     addMachine(){
      const newNodeId = String.fromCharCode(97 + this.nodes.length % 26);
    const customNode = {
    nodeId: newNodeId,
    name: newNodeId,
  };
    this.nodes.push(customNode);
      console.log(this.nodes);
     },
  
     initialize()
     {
      this.adjacency=[]
      for (var i = 0; i < this.machines.length; i++) {
          var row = [];
          for (var j = 0; j < this.queues.length; j++) {
              row.push(0); // Initialize each element to 0
          }
          this.adjacency.push(row);
      }
  
     }
  
    },
     };
  </script>
  
  <style>
 
  p{
    font-family: Helvetica, Arial, sans-serif;
    /* font-size: small; */
  }
  Button {
    /* margin: 10px 5px 1px 10px;
    margin-left: 1px;
    margin-right: 1px;
    margin-top: 1px;
    margin-bottom: 1px; */
    height: 75px;
    width: 75px;
    border-radius: 20%;
    font-size: 25px;
    background-color: rgb(208, 207, 207);
  }
  Button:hover{
    background-color:rgb(137, 135, 135);;
  }
  
  .sim-controls {
    border-radius: 50%;
    display: flex;
    /* justify-content: space-between; */
  }
  
  .buttonsbar {
    display: flex;
    justify-content: space-around;
    font-size: 20px;
    widows: 100%;
  }
  
  .machine {
    justify-content: center;
    display: block;
    
  }
  .display-screen {
     border: 2px solid #000000;
     background-color: white;
     position: absolute;
     top: 25%;
     width: 99%;
   }
   .contain{
    height: 100%;
   }
   
   .modal {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 600px;
    height: 150px;
    background-color: rgb(250, 246, 247);
    z-index: 2;
    border: 2px solid #000000;  /* '2px' is the width, 'solid' is the style, '#000000' is the color */

}
  .overlay{
    top: 0%;
    left: 0%;
    height: 100%;
    width: 100%;
    position:absolute;
    z-index: 1;
    background-color: rgba(0, 0, 0, 0.4);  /* Adjust alpha for desired transparency */
  }
  </style>