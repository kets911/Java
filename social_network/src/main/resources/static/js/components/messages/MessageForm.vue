<template>
    <div>
        <input type="text" placeholder="Write something" v-model="message" />
        <input type="button" value="Save" @click="save" />
    </div>
</template>

<script>
    function getIndex(list, id) {
        for (var i = 0; i < list.length; i++ ) {
            if (list[i].id === id) {
                return i
            }
        }

        return -1
    }

    export default {
        props: ['messages', 'messageAttr'],
        data() {
            return {
                message: '',
                id: ''
            }
        },
        watch: {
            messageAttr(newVal, oldVal) {
                this.message = newVal.text
                this.id = newVal.id
            }
        },
        methods: {
            save() {
                const message = { message: this.message }

                if (this.id) {
                    this.$resource('/messages{/id}').update({id: this.id}, message).then(result =>
                        result.json().then(data => {
                            const index = getIndex(this.messages, data.id)
                            this.messages.splice(index, 1, data)
                            this.message = ''
                            this.id = ''
                        })
                    )
                } else {
                    this.$resource('/messages{/id}').save({}, message).then(result =>
                        result.json().then(data => {
                            this.messages.push(data)
                            this.message = ''
                        })
                    )
                }
            }
        }
    }
</script>

<style>

</style>