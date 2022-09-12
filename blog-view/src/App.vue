<script setup>
// This starter template is using Vue 3 <script setup> SFCs
// Check out https://vuejs.org/api/sfc-script-setup.html#script-setup
</script>

<template>
  <router-view/>
</template>

<style>
#app {
  font-family: "PingFang SC", "Hiragino Sans GB", "Heiti SC", "Microsoft YaHei", "WenQuanYi Micro Hei", sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.header-anchor {
  float: left;
  margin-left: -0.87em;
  padding-right: 0.23em;
  font-weight: 500;
  transition: color .25s, opacity .25s;
  text-decoration-style: dotted;
  text-decoration: inherit;
  opacity: 0 !important;
  z-index: 20 !important;
  position: relative !important;
}

.header-anchor:hover {
  opacity: 1 !important;
}

p > img {
  max-width: 100%;
  border-radius: 4px;
}

table {
  width: 95%;
  margin: 0 auto;
  table-layout: auto;
}

.hljs {
  border-radius: 5px !important;
}

h1:target, h2:target, h3:target {
  padding-top: 90px;
}

h1::before, h2::before, h3::before, h4::before, h5::before, h6::before {
  position: absolute;
  left: auto;
  right: calc(100% + .75em);
  /*top: 0;*/
  color: #5b5b5b;
  font-size: 90%;
  font-weight: bold;
}

h1::before {
  content: 'H1';
}

h2::before {
  content: 'H2';
}

h3::before {
  content: 'H3';
}

h4::before {
  content: 'H4';
}

h5::before {
  content: 'H5';
}

h6::before {
  content: 'H6';
}

h1 {
  margin: 30px 0 !important;
  font-size: 2.7rem !important;
  font-weight: bold !important;
}

h2 {
  font-weight: 850 !important;
  display: block;
  margin: 0.83em 0 !important;
  padding-bottom: 0.3rem !important;
  font-size: 1.8rem !important;
  border-bottom: 2px solid #eaecef !important;
}

h3 {
  font-weight: 800 !important;
  margin: 18px 0 !important;
  font-size: 1.5rem !important;
}

h4 {
  font-weight: 750 !important;
  font-size: 1.3rem !important;
  margin: 8px 0 !important;
}

h5 {
  font-weight: 750 !important;
  font-size: 1.2rem !important;
  margin: 5px 0 !important;
}

h6 {
  font-weight: 750 !important;
  font-size: 1.1rem !important;
  margin: 3px 0 !important;
}

.ulClass {
  margin: 0;
  padding-left: 25px;
  color: #4285f4;
  list-style-type: upper-roman;
}

p > code {
  border-radius: 6px;
  padding: 0.2em 0.4em;
  font-size: 85%;
  font-family: Consolas, Liberation Mono, monospace;
}

blockquote {
  padding: 10px 15px;
  border-left: 6px rgba(128, 128, 128, 0.52) solid;
  background-color: rgba(119, 119, 119, 0.2);
}

table {
  margin: 10px 10px;
}

td, th {
  border: 1px #222222 solid;
  padding: 3px 6px;
}

th {
  background-color: rgba(34, 34, 34, 0.12);
}

@media (prefers-color-scheme: dark) {
  #app {
    background-color: #020407 !important;
  }


  h1, h2, h3, h4, h5, h6 {
    color: #eaeaea !important;
  }

  td, th {
    border: 1px rgba(255, 255, 255, 0.51) solid;
  }

  th {
    background-color: rgba(34, 34, 34, 0.5);
  }

  p > code {
    background-color: #2c2c2c;
    color: #4285f4;
  }

  .el-drawer, .--el-drawer-bg-color {
    background-color: black !important;
  }
}
</style>
