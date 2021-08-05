<template>
  <div id="app">
    <file-pond
      name="test"
      ref="pond"
      label-idle="Drop files here..."
      v-bind:allow-multiple="true"
      accepted-file-types="image/jpeg, image/png"
      :server="myServer"
      instantUpload ="false"
      v-bind:files="myFiles"
      v-on:init="handleFilePondInit"
      allowImageExifOrientation = "true"
      allowImagePreview="true"
    />
  </div>
</template>

<script>
// Import Vue FilePond
import vueFilePond from "vue-filepond";

// Import FilePond styles
import "filepond/dist/filepond.min.css";

// Import FilePond plugins
// Please note that you need to install these plugins separately

// Import image preview plugin styles
import "filepond-plugin-image-preview/dist/filepond-plugin-image-preview.min.css";

// Import image preview and file type validation plugins
import FilePondPluginFileValidateType from "filepond-plugin-file-validate-type";
import FilePondPluginImagePreview from "filepond-plugin-image-preview";
// 메타데이터 삽입
import FilePondPluginFileMetadata from 'filepond-plugin-file-metadata';
import FilePondPluginImageExifOrientation from 'filepond-plugin-image-exif-orientation';

// Create component
const FilePond = vueFilePond(
  FilePondPluginFileValidateType,
  FilePondPluginImagePreview,
  FilePondPluginFileMetadata,
  FilePondPluginImageExifOrientation
);

export default {
  name: "app",
  data: function () {
    return { 
      myFiles: ["cat.jpeg"],
      myServer: {
        process: (field, file, metadata, orientation, load) => {
          console.log("check what I can get", field, file);
          console.log(orientation)
          load(Date.now())
        }
      } 
    };
  },

  
  methods: {
    handleFilePondInit: function () {
      console.log("FilePond has initialized");

      // FilePond instance methods are available on `this.$refs.pond`
      console.log(this.$refs.pond)
    },
  },
  components: {
    FilePond,
  },
};
</script>