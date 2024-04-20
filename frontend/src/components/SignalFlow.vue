<template>
    <div class="contain">
      

      <div class="buttonsbar">
        <div class="machine">
          <button @click="addMachine">N+</button>
          <p>Add Node</p>
        </div>
        <div style="display: flex; flex-direction: row;">
          <p style="font-size: 25px;">No. of nodes: </p>
          <InputNumber style="width: 150px; height: 40px; position: relative; margin-top: 18px; margin-left: 5px;" v-model="numnodes" inputId="minmax" :min="2" :max="200" />
        </div>
        <div style="display: flex; flex-direction: row;">
          <p style="font-size: 25px; margin-left: -200px;">No. of numeric branches gain: </p>
          <InputNumber style="width: 150px; height: 40px; position: relative; margin-top: 18px; margin-left: 5px;" v-model="gains" inputId="minmax" :min="2" :max="200" />
        </div>
        <div style="display: flex; flex-direction: row;">
          <button @click="initialize" style="width: 150px; margin-left: -50px;">Save Values</button>
          <button @click="showmodal=true" style="width: 120px; margin-left: 200px">Connect</button>
        </div>
        <button @click="clear" class="pi pi-trash"></button>
  
        <div class="sim-controls">
          <button @click="simulate" style="margin-right: 10px;" class="pi pi-play"></button>
          <button @click="showresult=!showresult" style="margin-right: 10px;" class="pi pi-eye"></button>

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
      <div class="overlay" v-if="showmodal"></div>
      <div v-if="showresult" class="modal" style="width: 1900px; height: 1400px; background-color: cyan; ">
        <p style="font-size: 50px; font-weight: 600;">Final Values</p>
        <p style="font-weight: 550; display: flex; margin-left: 25px; align-items: flex-start; font-size: 35px; ">Forward Paths:</p>
        <div style="display: flex; flex-direction: column; justify-content:flex-start; align-items: flex-start; margin-left: 25px; margin-top: -25px; margin-right: 25px; font-size: 30px; overflow-y: auto; height: 200px; background-color: gray; border: 5px solid #000;;">
        
        <div v-for="(path, index) in forwardPaths" :key="index" style="margin-left: 10px;" >
        <div style="display: flex; justify-content: space-between;">
        <h3>Path {{ index+1 }} </h3>
        <h3  style="margin-left:677px;">{{ path[0] }}</h3>
        <h3 style="margin-left:840px;">Gain={{ path[1] }}</h3>
        </div>
        <!-- <button class="flag-btn" v-if="task.priority"><i class="pi pi-flag"></i></button>
        <button class="view-btn" @click="view(index)"><i class="pi pi-eye"></i></button>
        <button  @click="editTask(index)" class="edit-btn"  ><i class="pi pi-pencil"></i></button>
        <button class="delete-btn" @click="deleteTask(index)"><i class="pi pi-trash"></i> </button>
        <hr :style="{ 'top': getTopPosition(index) + '%', 'border-color': '#d0cdcd', 'height': '2px', 'border-width': '0 0 2px 0'}"> -->
  
        
  
      </div>
        </div>
        <p style="font-weight: 550; display: flex; margin-left: 25px; align-items: flex-start; font-size: 35px; ">Forward Paths:</p>
<!-- continue here -->
        
    </div>
      

    </div>
    
    <div class="canvas">
         <div ref="container" class="display-screen"></div>     
    </div>
  </template>
  
  <script>
  import Konva from 'konva'
  import InputNumber from 'primevue/inputnumber';
  import axios from 'axios'
  // import { Stage } from 'konva/lib/Stage';
  
  export default {
    components:{
      InputNumber,
      
    },
  
  data(){
  
    return{
      tr: new Konva.Transformer({
        borderStrokeWidth: 7, // Set the border width
        }),
      nodes:[],
      selected:[],
      adjacency:[],
      linelist:[],
      forwardPaths:[],
      numnodes:2,
      gains:1,
      gainamount:1,
      showmodal:false,
      showresult:false,
  
    };
  },
  
  mounted() {
       
       const screenWidth = 2940;
       const screenHeight = 850;
   
       this.stage = new Konva.Stage({
         container: this.$refs.container,
         width: screenWidth,
         height: screenHeight,
       });
   
      //  this.stage.on('dragend', (event) => {
      //  const shape = event.target;
      //  });
       this.layer = new Konva.Layer();
       this.stage.add(this.layer);
       this.layer.add(this.tr);
       this.tr.enabledAnchors([]);
       this.tr.rotateEnabled(false); // Disable rotation
      this.tr.anchorCornerRadius(0);
      this.forwardPaths.push(["p1","abc",30]);
      this.forwardPaths.push(["p2","bcd",15]);
      // this.forwardPaths.push(["p2",15]);
      // this.forwardPaths.push(["p2",15]);
      // this.forwardPaths.push(["p2",15]);

  
  
      // Set up an interval to call the fetchData function every 100 milliseconds
      // this.intervalId = setInterval(this.fetchData, 3000);
       
       
       
     }, 
     methods:{
  
      clear()
      {
        //write here the api responsible for clearing everything in the backend 
  
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
  
  
  //     fetchData() {
  //       const base = this.url+this.mode;
  //       console.log(base);
  //       axios.get(base).then(response => {
    
  //   console.log(response.data);  
  //   let todisplay = response.data;
  //   for(const iterator in todisplay)
  //   {
  //     if (todisplay[iterator].id.startsWith('Q')) {
  //     // For 'Q' items
  //     const queueId = todisplay[iterator].id;  // "Q0", "Q1", ...
  //     const numericPart = queueId.slice(1);
  //     const numberr = parseInt(numericPart, 10);
  //     const numberOfElements = todisplay[iterator].numberOfElements;
  //     this.queues[numberr].children[2].text(numberOfElements)
  
  //     // Use the data for queues
  //     // console.log(`Queue ID: ${queueId}, Number of Elements: ${numberOfElements}`);
  //   } else if (todisplay[iterator].id.startsWith('M')) {
  //     // For 'M' items
  //     const machineId = todisplay[iterator].id;  // "M1", "M2", ...
  //     const numericPart = machineId.slice(1);
  //     const numberr = parseInt(numericPart, 10);
  //     // const numberOfElements = todisplay[iterator].numberOfElements;
  //     const colorr = todisplay[iterator].colour;
  //     const red = parseInt(colorr[0], 10);
  //     const green = parseInt(colorr[1], 10);
  //     const blue = parseInt(colorr[2], 10);
  
  //     // Create an RGB color string
  //     const color = `rgb(${red}, ${green}, ${blue})`;
  //     this.machines[numberr].children[0].fill('#008000');
  
  //     this.machines[numberr].children[0].fill(color);
  
      
  
  //     // Use the data for machines
  //     // console.log(`Machine ID: ${machineId}, Number of Elements: ${numberOfElements}`);
  //   }
  
  
  //   }   
    
  //   // Assume 'jsonResponse' is the JSON response you received
  
  // // Filter and process the data
  
  
  //     })
  //     .catch(error => {
  //       console.error('There was an error!', error);
        
  //     })
  //       // console.log('Fetching data...');
  //     },
  
  
      startFetchData1() {
        // Clear the interval for fetchData2 if it's running
        clearInterval(this.fetchData2Interval);
  
        // Set up an interval for fetchData1
        this.fetchData1Interval = setInterval(this.fetchData1, 3000);
      },
      startFetchData2() {
        // Clear the interval for fetchData1 if it's running
        clearInterval(this.fetchData1Interval);
  
        // Set up an interval for fetchData2
        this.fetchData2Interval = setInterval(this.fetchData2, 3000);
      },
  
      fetchData1() {
        // console.log('Fetching data 1...');
        // Your fetchData1 logic here
        axios.get('http://localhost:8081/live').then(response => {
    
    console.log(response.data);  
    let todisplay = response.data;
    for(const iterator in todisplay)
    {
      if (todisplay[iterator].id.startsWith('Q')) {
      // For 'Q' items
      const queueId = todisplay[iterator].id;  // "Q0", "Q1", ...
      const numericPart = queueId.slice(1);
      const numberr = parseInt(numericPart, 10);
      const numberOfElements = todisplay[iterator].numberOfElements;
      this.queues[numberr].children[2].text(numberOfElements)
  
      // Use the data for queues
      // console.log(`Queue ID: ${queueId}, Number of Elements: ${numberOfElements}`);
    } else if (todisplay[iterator].id.startsWith('M')) {
      // For 'M' items
      const machineId = todisplay[iterator].id;  // "M1", "M2", ...
      const numericPart = machineId.slice(1);
      const numberr = parseInt(numericPart, 10);
      // const numberOfElements = todisplay[iterator].numberOfElements;
      const colorr = todisplay[iterator].colour;
      const red = parseInt(colorr[0], 10);
      const green = parseInt(colorr[1], 10);
      const blue = parseInt(colorr[2], 10);
  
      // Create an RGB color string
      const color = `rgb(${red}, ${green}, ${blue})`;
      this.machines[numberr].children[0].fill('#008000');
  
      this.machines[numberr].children[0].fill(color);
  
      
  
      // Use the data for machines
      // console.log(`Machine ID: ${machineId}, Number of Elements: ${numberOfElements}`);
    }
  
  
    }   
    
    // Assume 'jsonResponse' is the JSON response you received
  
  // Filter and process the data
  
  
      })
      .catch(error => {
        console.error('There was an error!', error);
        
      })
        // console.log('Fetching data...');
      },
  
  
  
      
  
      simulate(){
        console.table(this.adjacency);
        // const body={architecture:this.adjacency}
        let stringArray = this.adjacency.map(subArray => 
    subArray.map(element => element.toString())
);

        axios.post(`http://localhost:8080/create?numberOfNodes=${this.numnodes}`,stringArray);
  
        setTimeout(() => {
          axios.get(`http://localhost:8080/forwardPaths`).then(response => {
    
    console.log(response.data); 
    this.forwardPaths=response.data;
    })
}, 1000);

        
  
      },
  
  
  
  
  
      fetchData2() {
        //  console.log('Fetching data 2...');
        // Your fetchData1 logic here
        axios.get('http://localhost:8081/live').then(response => {
    
    console.log(response.data);  
    let todisplay = response.data;
    for(const iterator in todisplay)
    {
      if (todisplay[iterator].id.startsWith('Q')) {
      // For 'Q' items
      const queueId = todisplay[iterator].id;  // "Q0", "Q1", ...
      const numericPart = queueId.slice(1);
      const numberr = parseInt(numericPart, 10);
      const numberOfElements = todisplay[iterator].numberOfElements;
      if(numberr===this.queues.length-1)
      this.queues[numberr].children[2].text(numberOfElements-this.number)
      else
      this.queues[numberr].children[2].text(numberOfElements)
  
      // Use the data for queues
      // console.log(`Queue ID: ${queueId}, Number of Elements: ${numberOfElements}`);
    } else if (todisplay[iterator].id.startsWith('M')) {
      // For 'M' items
      const machineId = todisplay[iterator].id;  // "M1", "M2", ...
      const numericPart = machineId.slice(1);
      const numberr = parseInt(numericPart, 10);
      // const numberOfElements = todisplay[iterator].numberOfElements;
      const colorr = todisplay[iterator].colour;
      const red = parseInt(colorr[0], 10);
      const green = parseInt(colorr[1], 10);
      const blue = parseInt(colorr[2], 10);
  
      // Create an RGB color string
      const color = `rgb(${red}, ${green}, ${blue})`;
      this.machines[numberr].children[0].fill('#008000');
  
      this.machines[numberr].children[0].fill(color);
  
      
  
      // Use the data for machines
      // console.log(`Machine ID: ${machineId}, Number of Elements: ${numberOfElements}`);
    }
  
  
    }   
    
    // Assume 'jsonResponse' is the JSON response you received
  
  // Filter and process the data
  
  
      })
      .catch(error => {
        console.error('There was an error!', error);
        
      })
        // console.log('Fetching data...');
      },
  
  
  
  
      replay(){
        axios.get('http://localhost:8081/replay');
        this.startFetchData2();
      },
  
      connect(){
      
     
       if(this.tr.nodes().length==2){
  
        const line = new Konva.Line({
            points: [this.tr.nodes()[1].x()+100,this.tr.nodes()[1].y()+50,
             this.tr.nodes()[0].x(), this.tr.nodes()[0].y()],
            stroke: 'yellow',
            strokeWidth: 4,
           
          });
          // console.log('55555')
          // console.log(this.tr.nodes()[0].children[0])
  
  
          // Add the line to the layer
          this.layer.add(line);
          this.linelist.push(line);
  
          // Redraw the layer
          this.layer.draw();
          this.adjacency.push([this.tr.nodes()[0].children[1].text(),this.tr.nodes()[1].children[1].text(),this.gainamount]);
          this.gainamount=1;
          this.showmodal=false;
          // this.adjacency[this.tr.nodes()[0].children[0].id()][this.tr.nodes()[1].children[0].id()]=1;
          console.table(this.adjacency)
          
       
  
       
      }
      
        
      },
  
      addDoubleClickEvent(shape) {
     shape.on('click', () => {
       shape.moveToTop();
       if(this.selected.length<2)
       {
        this.selected.push(shape);
        this.tr.nodes(this.selected);
       }
       else{
        this.selected=[];
        this.selected.push(shape);
        this.tr.nodes(this.selected);
  
       }
       
       this.stage.batchDraw();
     });
   
     this.stage.on('click', (e) => {
       if (e.target === this.stage) {
         if (this.tr.nodes().length > 0) {
           // Commented out to prevent calling onModify when clicking outside the shape
           // this.onModify(this.tr.nodes()[0]);
         }
          this.tr.nodes([]);
          this.selected=[];
         return;
       }
     });},
    
     addMachine(){
         var circle = new Konva.Group({
            x: 150,
            y: 150,
            draggable: true,
        });
  
        circle.add(new Konva.Circle({
            radius: 75,
            fill: '#008000',
            draggable: true,
            id:this.nodes.length,
        }));
        let x ="a";
        if(this.nodes.length===0)
        {
            x = "R";
        }
        else if(this.nodes.length===this.numnodes-1)
      {
        x = "C";
      }
      else{
        var code = x.charCodeAt(0);
        code+=this.nodes.length-1;
        x=String.fromCharCode(code);
      }
        circle.add(new Konva.Text({
            text:x,
            fontSize: 50,
            fontFamily: 'Calibri',
            fill: 'black',
            width: 100,
            padding:5,
            height:100,
            align: 'center',
            verticalAlign: 'middle',
            offsetX:50,
            offsetY:45,
           
        }));
              
  
         this.addDoubleClickEvent(circle);
         this.layer.add(circle);
         this.nodes.push(circle);
         this.layer.draw();
  
         
  
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
  
  }
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