<template>
  <div
      class="flex flex-col md:flex-row md:justify-center items-center w-full mt-4"
  >
    <div class="flex items-center">
      <ul class="flex space-x-2 rtl:flex-row-reverse">
        <li
            class="page-item"
            :class="{ 'opacity-50 pointer-events-none': isInFirstPage }"
        >
          <a
              class="page-link p-2 border rounded text-white bg-pink-500 hover:bg-pink-300"
              @click="onClickFirstPage"
          >
            <i class="fas fa-angle-double-left" />
          </a>
        </li>

        <li
            class="page-item"
            :class="{ 'opacity-50 pointer-events-none': isInFirstPage }"
        >
          <a
              class="page-link p-2 border rounded text-white bg-pink-500 hover:bg-pink-300"
              @click="onClickPreviousPage"
          >
            <i class="fas fa-angle-left" />
          </a>
        </li>

        <li
            v-for="(page, i) in pages"
            :key="i"
            class="page-item"
        >
          <a
              class="page-link p-2 border rounded text-white bg-pink-500 hover:bg-pink-300"
              :class="{ 'bg-pink-700 text-white': isPageActive(page.name) }"

              @click="onClickPage(page.name)"
          >
            {{ page.name }}
          </a>
        </li>

        <li
            class="page-item"
            :class="{ 'opacity-50 pointer-events-none': isInLastPage }"
        >
          <a
              class="page-link p-2 border rounded text-white bg-pink-500 hover:bg-pink-300"
              @click="onClickNextPage"
          >
            <i class="fas fa-angle-right" />
          </a>
        </li>

        <li
            class="page-item"
            :class="{ 'opacity-50 pointer-events-none': isInLastPage }"
        >
          <a
              class="page-link p-2 border rounded text-white bg-pink-500 hover:bg-pink-300"
              @click="onClickLastPage"
          >
            <i class="fas fa-angle-double-right" />
          </a>
        </li>
      </ul>
    </div>
  </div>
</template>
<script lang="ts">
import { computed, defineComponent } from "vue";

export default defineComponent({
  name: "table-pagination",
  components: {},

  props: {
    maxVisibleButtons: {
      type: Number,
      required: false,
      default: 5,
    },
    totalPages: {
      type: Number,
      required: true,
    },
    total: {
      type: Number,
      required: true,
    },
    perPage: {
      type: Number,
      required: true,
    },
    currentPage: {
      type: Number,
      required: true,
    },
  },
  emits: ["page-change"],
  setup(props, { emit }) {
    const startPage = computed(() => {
      const floorVal = Math.floor(props.maxVisibleButtons / 2);

      if (
          props.totalPages < props.maxVisibleButtons ||
          props.currentPage === 1 ||
          props.currentPage <= Math.floor(props.maxVisibleButtons / 2) ||
          (props.currentPage + floorVal > props.totalPages &&
              props.totalPages === props.maxVisibleButtons)
      ) {
        return 1;
      }

      if (props.currentPage + floorVal > props.totalPages) {
        return props.totalPages - props.maxVisibleButtons + 1;
      }

      return props.currentPage - floorVal;
    });

    const endPage = computed(() => {
      return Math.min(
          startPage.value + props.maxVisibleButtons - 1,
          props.totalPages
      );
    });

    const pages = computed(() => {
      const range: Array<{
        name: number;
        isDisabled: boolean;
      }> = [];

      for (let i = startPage.value; i <= endPage.value; i += 1) {
        range.push({
          name: i,
          isDisabled: i === props.currentPage,
        });
      }

      return range;
    });

    const isInFirstPage = computed(() => {
      return props.currentPage === 1;
    });
    const isInLastPage = computed(() => {
      return props.currentPage === props.totalPages;
    });

    const onClickFirstPage = () => {
      emit("page-change", 1);
    };
    const onClickPreviousPage = () => {
      emit("page-change", props.currentPage - 1);
    };
    const onClickPage = (page: number) => {
      emit("page-change", page);
    };
    const onClickNextPage = () => {
      emit("page-change", props.currentPage + 1);
    };
    const onClickLastPage = () => {
      emit("page-change", props.totalPages);
    };
    const isPageActive = (page: number) => {
      return props.currentPage === page;
    };

    return {
      startPage,
      endPage,
      pages,
      isInFirstPage,
      isInLastPage,
      onClickFirstPage,
      onClickPreviousPage,
      onClickPage,
      onClickNextPage,
      onClickLastPage,
      isPageActive,
    };
  },
});
</script>

<style >
.pagination .page-item .page-link i {
  font-size: 1.5rem;
}
</style>
