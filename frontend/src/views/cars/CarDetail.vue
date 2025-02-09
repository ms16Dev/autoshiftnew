<script setup lang="ts">
import AdItem from "../../components/AdItem.vue";
import Comments from "../../components/Comments.vue";
import UserView from "../../components/UserView.vue";
import ImageViewer from "../../components/ImageViewer.vue";
import IconButton from "../../components/IconButton.vue";
import IconButtonH from "../../components/IconButtonH.vue";
import ChipItemCheckedSm from "../../components/car/ChipItemCheckedSm.vue";
import SharePost from "../../components/SharePost.vue";


defineOptions({
  name: 'car-details'
});
</script>

<template>
  <div class="flex md:flex-row-reverse flex-wrap w-full bg-gray-200">
    <div class="w-full md:w-1/5 p-4 text-center text-gray-400">
      <AdItem/>

    </div>
    <div class="w-full md:w-3/5  p-4 text-center text-gray-200">

      <div class="flex-col grid grid-cols-1 md:grid-cols-2 xl:grid-cols-2 -px-4 gap-3">

<!--        Headline-->

        <div class="flex flex-wrap text-3xl text-pink-500 font-extrabold col-span-2 text-center">
          <span>Toyota -</span>
          <span>Yaris -</span>
          <span>2024 -</span>
          <span>Korean</span>
        </div>
        <div class="flex flex-wrap text-xl text-gray-500 col-span-2 text-center items-center gap-2">
          <UserView :user="{ id: 1, name: 'John Doe', date: '3 weeks ago', avatar: 'https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg' }" />
          <div class="ml-auto">
            <SharePost />
          </div>
        </div>



        <ImageViewer class="w-full col-span-2 mb-8"
            :coverPhoto="'/car-phldr.png'"
            :imageList="[
                'https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg',
                'https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg',
                'https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg',
                'https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg',
                'https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg',
                'https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg',
                'https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg',
                'https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg',
                'https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg',
                'https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg',
                'https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg',
                'https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg',
                'https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg',
                'https://spine-mena.com/wp-content/uploads/2023/03/Lexus-Logo-Vector-730x730-1.jpeg',
            ]"
        />

        <h1 class="pt-12 pb-4 text-xl col-span-2 text-pink-500 font-extrabold">Specifications</h1>

        <div class="flex justify-center items-center flex-wrap col-span-2 gap-3">
          <IconButton icon="fa-tachometer" label="gfdgfd" />
          <IconButton icon="fa-gears" label="hfghfg" />
          <IconButton icon="fa-gas-pump" label="ghfhgf" />
          <IconButton icon="fa-bolt" label="sfdsfsd" />
          <IconButton icon="fa-car" label="reddddd" />
          <IconButton icon="fa-brush" label="dfsdsf" />
        </div>





        <div class="flex flex-col items-start col-span-2">

          <IconButtonH icon="fa-leaf" label="Luxury" />
          <div class="flex flex-wrap col-span-2 mb-8">
            <ChipItemCheckedSm option="jsajd;aslkdaskdask;l"/>
            <ChipItemCheckedSm option="jsakdask;l"/>
            <ChipItemCheckedSm option="jsajd;aslkdask;l"/>
            <ChipItemCheckedSm option="jsajd;aslk"/>
          </div>

          <IconButtonH icon="fa-life-ring" label="Safety" />
          <div class="flex flex-wrap col-span-2 mb-8">
            <ChipItemCheckedSm option="jsajd;aslk"/>
            <ChipItemCheckedSm option="jsajd;aslk"/>
            <ChipItemCheckedSm option="jsajd;aslk"/>
            <ChipItemCheckedSm option="jsajd;aslk"/>
            <ChipItemCheckedSm option="jsajd;aslk"/>

          </div>


          <IconButtonH icon="fa-info" label="Notes" />
          <p class="text-gray-400 text-lg mb-12">
            Lorem ipsum dolar lkdjsakds; dsa d sads sadsad sd sadqwerwq tg erywLorem ipsum dolar lkdjsakds; dsa d sads sadsad sd sadqwerwq tg erywLorem ipsum dolar lkdjsakds; dsa d sads sadsad sd sadqwerwq tg erywLorem ipsum dolar lkdjsakds; dsa d sads sadsad sd sadqwerwq tg erywLorem ipsum dolar lkdjsakds; dsa d sads sadsad sd sadqwerwq tg eryw

          </p>


          <Comments></Comments>




        </div>




      </div>




    </div>

    <div class="w-full md:w-1/5  p-4 text-center text-gray-700 fixed sticky">
      <AdItem/>

    </div>
  </div></template>


<style scoped>

</style>