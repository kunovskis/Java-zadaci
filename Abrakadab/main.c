#include<stdio.h>

int main(){
    char znak,suma=0,broj=0;
    while(1){
        scanf("%c", &znak);
        if(znak=='!')
            break;
        if(znak>='0' && znak<='9'){
            broj=broj*10+znak-48;
        }
        else{
            suma+=broj;
            broj=0;
        }
        printf("%d  %d\n", suma, broj);
    }
    printf("%d",suma);
    return 0;
}
